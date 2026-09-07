package com.ndt.capstone.service;

import java.time.Duration;
import java.util.List;


import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.entity.*;
import com.ndt.capstone.mapper.ProductMapper;
import com.ndt.capstone.payload.request.InsertProductRequest;
import com.ndt.capstone.repository.*;
import com.ndt.capstone.service.contract.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@Service
public class BrandServiceImpl implements BrandService {
    private final String brandAllCacheKey;

    private final BrandRepository brandRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer cacheDuration;

    public BrandServiceImpl(
        BrandRepository brandRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.product.prefix:brand}") String brandPrefixCacheKey,
        @Value(value = "${cache.product.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.brandRepository = brandRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.brandAllCacheKey = brandPrefixCacheKey + ":all";
    }


    @Override
    public List<ProductDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(brandAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<ProductDTO> products = brandRepository
                .findAll()
                .stream()
                .map(ele -> ProductMapper.toDTO(ele, defaultImage))
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                brandAllCacheKey,
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
        return brandRepository
            .findAll(pageable)
            .map(ele -> ProductMapper.toDTO(ele, defaultImage));
    }


    @Override
    public Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return brandRepository.findByNameContainingIgnoreCase(keyword, page)
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
        ProductEntity productInserted = brandRepository.save(product); // luu bang product
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
