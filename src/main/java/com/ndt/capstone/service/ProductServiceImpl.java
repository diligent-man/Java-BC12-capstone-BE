package com.ndt.capstone.service;

import java.util.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.Duration;
import java.util.stream.Collectors;


import jakarta.transaction.Transactional;


import lombok.extern.slf4j.Slf4j;


import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;



import com.ndt.capstone.entity.*;
import com.ndt.capstone.repository.*;
import com.ndt.capstone.dto.product.*;
import com.ndt.capstone.mapper.product.*;
import com.ndt.capstone.enums.exception.*;
import com.ndt.capstone.exception.product.*;
import com.ndt.capstone.payload.request.product.*;

import com.ndt.capstone.spec.ProductSpec;
import com.ndt.capstone.enums.file.UploadImageType;
import com.ndt.capstone.projection.product.ProductVariantRow;

import com.ndt.capstone.service.contract.FileService;
import com.ndt.capstone.service.contract.ProductService;

import com.ndt.capstone.dto.product.ProductDetailDTO;
import com.ndt.capstone.dto.product.ProductVariantDetailDTO;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final TagRepository tagRepo;

    private final SizeRepository sizeRepo;

    private final BrandRepository brandRepo;

    private final ColorRepository colorRepo;

    private final ProductRepository productRepo;

    private final CategoryRepository categoryRepo;

    private final ProductTagRepository productTagRepo;

    private final ProductVariantRepository productVariantRepo;

    private final ProductCategoryRepository productCategoryRepo;

    private final FileService fileService;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final String defaultImage;

    private final String uploadImagePath;

    private final String imageSeparator;

    private final String productAllCacheKey;

    private final String productDetailCacheKey;

    private final Integer cacheDuration;


    public ProductServiceImpl(
        TagRepository tagRepo,
        SizeRepository sizeRepo,
        BrandRepository brandRepo,
        ColorRepository colorRepo,
        ProductRepository productRepo,
        CategoryRepository categoryRepo,
        ProductTagRepository productTagRepo,
        ProductVariantRepository productVariantRepo,
        ProductCategoryRepository productCategoryRepo,
        FileService fileService,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${file.upload.image.default-image-name:default_cloth.jpg}") String defaultImage,
        @Value(value = "${file.upload.image.path:./data/upload/images}") String uploadImagePath,
        @Value(value = "${file.upload.image.default-image-separator:, }") String imageSeparator,
        @Value(value = "${cache.product.prefix:product}") String cacheKeyPrefix,
        @Value(value = "${cache.product.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.tagRepo = tagRepo;
        this.sizeRepo = sizeRepo;
        this.brandRepo = brandRepo;
        this.colorRepo = colorRepo;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.productTagRepo = productTagRepo;
        this.productVariantRepo = productVariantRepo;
        this.productCategoryRepo = productCategoryRepo;

        this.fileService = fileService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;

        this.defaultImage = defaultImage;
        this.uploadImagePath = uploadImagePath;
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
            List<ProductDTO> products = productRepo
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
            List<ProductVariantRow> rows = productRepo.findProductDetailByName(name);
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
        return productRepo
            .findAllByVariantsIsNotEmpty(pageable)
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
        return productRepo
            .findAll(spec, pageable)
            .map(ele -> ProductMapper.toDTO(ele, defaultImage));
    }


    @Override
    public List<ProductDTO> searchByName(String name) {
        return productRepo
            .findByNameContainingIgnoreCase(name)
            .stream()
            .map(p -> ProductMapper.toDTO(p, defaultImage))
            .toList();
    }


    @Override
    @Transactional
    public String insertProduct(InsertProductRequest req) {
        BrandEntity brand = brandRepo
            .findByNameContainingIgnoreCase(req.getBrandName())
            .orElseThrow(() -> new BrandException(BrandErrMsg.BRAND_NOT_FOUND));

        if (productRepo.existsByNameContainingIgnoreCaseAndBrand_Id(req.getName(), brand.getId()))
            throw new ProductException(ProductErrMsg.PRODUCT_EXISTED_BY_BRAND);

        ProductEntity saved = productRepo.save(ProductMapper.toEntity(req, brand));

        Set<CategoryEntity> categories = req.getCategoryNames()
            .stream()
            .map(ele -> categoryRepo
                .findByName(ele)
                .orElseThrow(() -> new CategoryException(CategoryErrMsg.CATEGORY_NOT_FOUND))
            )
            .collect(Collectors.toSet());

        productCategoryRepo.saveAll(
            categories.stream()
                .map(c -> new ProductCategoryEntity(saved, c))
                .toList()
        );

        Set<TagEntity> tags = req.getTagNames()
            .stream()
            .map(ele -> tagRepo
                .findByName(ele)
                .orElseThrow(() -> new TagException(TagErrMsg.TAG_NOT_FOUND))
            )
            .collect(Collectors.toSet());

        productTagRepo.saveAll(
            tags.stream()
                .map(c -> new ProductTagEntity(saved, c))
                .toList()
        );

        evictAfterCommit(productAllCacheKey);
        return saved.getName();
    }


    @Override
    @Transactional
    public void insertVariant(InsertVariantRequest req) {
        ProductEntity product = productRepo
            .findById(req.getIdProduct())
            .orElseThrow(() -> new ProductException(ProductErrMsg.PRODUCT_NOT_FOUND));

        ColorEntity color = colorRepo
            .findByNameContainingIgnoreCase(req.getColorName())
            .orElseThrow(() -> new ColorException(ColorErrMsg.COLOR_NOT_FOUND));

        SizeEntity size = sizeRepo
            .findByNameContainingIgnoreCase(req.getSizeName())
            .orElseThrow(() -> new SizeException(SizeErrMsg.SIZE_NOT_FOUND));

        if (productVariantRepo.existsByProductIdAndColorIdAndSizeId(product.getId(), color.getId(), size.getId()))
            throw new ProductException(ProductErrMsg.VARIANT_EXISTED);

        List<String> uploadedURIs = new ArrayList<>();
        for (MultipartFile file : req.getFiles()) {
            Path dst = Paths.get(
                uploadImagePath,
                UploadImageType.PRODUCT.getFolder(),
                req.getBrandName(),
                product.getName()
            );

            fileService.save(file, dst.toString());
            uploadedURIs.add(file.getOriginalFilename());
        }

        ProductVariantEntity variant = new ProductVariantEntity();
        variant.setProduct(product);
        variant.setColor(color);
        variant.setSize(size);
        variant.setQuantity(req.getQuantity());
        variant.setPrice(req.getPrice());
        variant.setImages(String.join(imageSeparator, uploadedURIs));

        productVariantRepo.save(variant);
        evictAfterCommit(productAllCacheKey, productDetailCacheKey + product.getName());
    }


    private void evictAfterCommit(String... keys) {
        Runnable evict = () -> {
            try {
                redisTemplate.delete(Arrays.asList(keys));
            } catch (Exception e) {
                // A failed eviction must not fail the request; the TTL will clean up eventually.
                log.warn("Cache eviction failed for keys {}", Arrays.toString(keys), e);
            }
        };

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    evict.run();
                }
            });
        } else {
            evict.run();
        }
    }
}
