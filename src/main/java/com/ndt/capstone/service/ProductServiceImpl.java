package com.ndt.capstone.service;

import java.util.*;
import java.time.Duration;


import com.ndt.capstone.mapper.product.*;
import com.ndt.capstone.payload.request.product.InsertVariantRequest;
import jakarta.persistence.*;

import jakarta.transaction.Transactional;


import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import com.ndt.capstone.entity.*;

import com.ndt.capstone.dto.product.ProductDTO;
import com.ndt.capstone.spec.ProductSpec;


import com.ndt.capstone.enums.exception.ProductErrMsg;

import com.ndt.capstone.repository.ProductRepository;
import com.ndt.capstone.repository.ProductVariantRepository;

import com.ndt.capstone.service.contract.FileService;
import com.ndt.capstone.service.contract.ProductService;

import com.ndt.capstone.exception.product.ProductException;
import com.ndt.capstone.projection.product.ProductVariantRow;

import com.ndt.capstone.payload.request.product.ProductFilterRequest;
import com.ndt.capstone.payload.request.product.InsertProductRequest;

import com.ndt.capstone.dto.product.ProductDetailDTO;
import com.ndt.capstone.dto.product.ProductVariantDetailDTO;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final FileService fileService;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final String defaultImage;

    private final String imageSeparator;

    private final String productAllCacheKey;

    private final String productDetailCacheKey;

    private final Integer cacheDuration;

    @PersistenceContext
    private EntityManager entityManager;


    public ProductServiceImpl(
        ProductRepository productRepository,
        ProductVariantRepository productVariantRepository,
        FileService fileService,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        EntityManager entityManager,
        @Value(value = "${file.upload.image.default-image-name:default_cloth.jpg}") String defaultImage,
        @Value(value = "${file.upload.image.default-image-separator:, }") String imageSeparator,
        @Value(value = "${cache.product.prefix:product}") String cacheKeyPrefix,
        @Value(value = "${cache.product.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.fileService = fileService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;

        this.defaultImage = defaultImage;
        this.imageSeparator = imageSeparator;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.productAllCacheKey = cacheKeyPrefix + ":all";
        this.productDetailCacheKey = cacheKeyPrefix + ":detail:";
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
    public ProductDetailDTO getProductDetail(String name) {
        try {
            String cacheKey = productDetailCacheKey + name;

            String cache = redisTemplate.opsForValue().get(cacheKey);
            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<ProductVariantRow> rows = productRepository.findProductDetailByName(name);
            if (rows.isEmpty()) {
                throw new ProductException(ProductErrMsg.PRODUCT_NOT_FOUND, String.format("Product (%s) not found: ", name));
            }

            List<ProductVariantDetailDTO> variants = ProductVariantDetailMapper.toDTO(rows, defaultImage, imageSeparator);
            ProductDetailDTO productDetail = ProductDetailMapper.toDTO(rows.getFirst(), variants);

            // Caching
            redisTemplate
                .opsForValue()
                .set(
                    cacheKey,
                    objectMapper.writeValueAsString(productDetail),
                    Duration.ofMillis(cacheDuration)
                );
            return productDetail;
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
    public Page<ProductDTO> filterProduct(ProductFilterRequest req, Pageable pageable) {
        Specification<ProductEntity> spec = ProductSpec.build(
            req.getName(),
            req.getCategories(),
            req.getTags(),
            req.getBrands(),
            req.getPriceRanges()
        );
        return productRepository
            .findAll(spec, pageable)
            .map(ele -> ProductMapper.toDTO(ele, defaultImage));
    }

    @Override
    public List<ProductDTO> searchByName(String name) {
        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(p -> ProductMapper.toDTO(p, defaultImage))
                .toList();
    }

    @Override
    @Transactional
    // biến nguyên hàm được đặt trên thành 1 giao dịch, nếu cả hàm chạy thành công thì mới thực hiện truy vấn tới database
    public Long insertProduct(InsertProductRequest productRequest) {
        ProductEntity product = new ProductEntity();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setInformation(productRequest.getInformation());
        product.setPrice(productRequest.getPrice());
        BrandEntity brand = entityManager.getReference(BrandEntity.class, productRequest.getIdBrand());
        product.setBrand(brand);
        ProductEntity saved = productRepository.save(product);
        return saved.getId();   // trả về id để FE dùng ở bước 2
    }

    @Override
    @Transactional
    public void insertVariant(InsertVariantRequest variantRequest) {
        fileService.save(variantRequest.getFile()); // lưu file ảnh

        ProductEntity product = productRepository
            .findById(variantRequest.getIdProduct())
            .orElseThrow(() -> new RuntimeException("Product not found: " + variantRequest.getIdProduct()));

        ColorEntity color = entityManager.getReference(ColorEntity.class, variantRequest.getIdColor());
        SizeEntity  size  = entityManager.getReference(SizeEntity.class,  variantRequest.getIdSize());

        ProductVariantEntity variant = new ProductVariantEntity();
        variant.setProduct(product);
        variant.setColor(color);
        variant.setSize(size);
        variant.setQuantity(variantRequest.getQuantity());
        variant.setPrice(product.getPrice());
        variant.setImages(variantRequest.getFile().getOriginalFilename());

        productVariantRepository.save(variant);
    }
}
