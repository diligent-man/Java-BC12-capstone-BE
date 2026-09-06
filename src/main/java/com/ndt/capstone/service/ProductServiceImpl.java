package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import jakarta.persistence.*;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import com.ndt.capstone.entity.*;

import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.mapper.ProductMapper;
import com.ndt.capstone.payload.request.InsertProductRequest;

import com.ndt.capstone.repository.ProductRepository;
import com.ndt.capstone.repository.VariantRepository;

import com.ndt.capstone.service.contract.FileService;
import com.ndt.capstone.service.contract.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

    private final String productAllCacheKey;

    private final ProductRepository productRepository;

    private final VariantRepository variantRepository;

    private final FileService fileService;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final String defaultImage;

    private final Integer cacheDuration;

    @PersistenceContext
    private EntityManager entityManager;


    public ProductServiceImpl(
        ProductRepository productRepository,
        VariantRepository variantRepository,
        FileService fileService,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        EntityManager entityManager,
        @Value(value = "${upload.default-image:default_cloth.jpg}") String defaultImage,
        @Value(value = "${cache.product.prefix:product}") String productPrefixCacheKey,
        @Value(value = "${cache.product.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.fileService = fileService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;

        this.defaultImage = defaultImage;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.productAllCacheKey = productPrefixCacheKey + ":all";
    }


    @Override
    public List<ProductDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(productAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<ProductDTO> products = productRepository
                .findAll()
                .stream()
                .map(ele -> ProductMapper.toDTO(ele, defaultImage))
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                productAllCacheKey,
                objectMapper.writeValueAsString(products),
                Duration.ofMillis(cacheDuration)
            );

            return products;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }

    @Override
    public Page<ProductDTO> getPagedProducts(Pageable pageable) {
        return productRepository
            .findAll(pageable)
            .map(ele -> ProductMapper.toDTO(ele, defaultImage));
    }


    @Override
    public Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCase(keyword, page)
            .map(product -> ProductMapper.toDTO(product, defaultImage));
    }


    @Override
    @Transactional
    // biến nguyên hàm được đặt trên thành 1 giao dịch, nếu cả hàm chạy thành công thì mới thực hiện truy vấn tới database
    public void insertProduct(InsertProductRequest productRequester) {
        fileService.save(productRequester.getFile()); // lưu hình

        ProductEntity product = new ProductEntity();
        product.setName(productRequester.getName());
        product.setDescription(productRequester.getDescription());
        product.setPrice(productRequester.getPrice());

        BrandEntity brand = entityManager.getReference(BrandEntity.class, productRequester.getIdBrand());
        product.setBrand(brand);
        ProductEntity productInserted = productRepository.save(product); // luu bang product
        // jpa mặc định sẽ trả ra dòng dữ liệu vừa insert để có thể tiếp tục lấy id product để truy vấn vào bảng variant, nếu làm chay phải lấy truy vấn lấy id max

        ColorEntity color = entityManager.getReference(ColorEntity.class, productRequester.getIdColor());
        SizeEntity size = entityManager.getReference(SizeEntity.class, productRequester.getIdSize());

        VariantEntity variantProduct = new VariantEntity();
        variantProduct.setProduct(productInserted);
        variantProduct.setColor(color);
        variantProduct.setIdSize(size);
        variantProduct.setImages(productRequester.getFile().getOriginalFilename()); // lấy tên hình để lưu vào bảng variant

        variantRepository.save(variantProduct); // luu bang varint, phai luu ca 2 bang cung luc
    }

}
