package com.ndt.capstone.service.impl;

import java.util.*;

import java.time.Duration;
import java.util.stream.Collectors;


import com.ndt.capstone.config.props.UploadFileProps;
import com.ndt.capstone.config.props.cache.ProductCacheProps;
import com.ndt.capstone.service.contract.*;
import com.ndt.capstone.service.contract.CacheService;
import com.ndt.capstone.service.contract.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import tools.jackson.core.type.TypeReference;


import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.entity.*;
import com.ndt.capstone.repo.*;
import com.ndt.capstone.dto.product.*;
import com.ndt.capstone.mapper.product.*;
import com.ndt.capstone.enums.exception.*;
import com.ndt.capstone.exception.product.*;
import com.ndt.capstone.payload.req.product.*;

import com.ndt.capstone.spec.ProductSpec;
import com.ndt.capstone.projection.product.ProductVariantRow;

import com.ndt.capstone.dto.product.ProductDetailDTO;
import com.ndt.capstone.dto.product.ProductVariantDetailDTO;

import static com.ndt.capstone.utils.ImageUtils.buildVariantImageSavePath;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final TagRepo tagRepo;

    private final SizeRepo sizeRepo;

    private final BrandRepo brandRepo;

    private final ColorRepo colorRepo;

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final ProductTagRepo productTagRepo;

    private final ProductVariantRepo productVariantRepo;

    private final ProductCategoryRepo productCategoryRepo;

    private final FileService fileService;

    private final UploadFileProps uploadFileProps;

    private final CacheService cacheService;

    private final ProductCacheProps cacheProps;


    private ProductDetailDTO loadProductDetail(String name) {
        List<ProductVariantRow> rows = productRepo.findProductDetailByName(name);
        if (rows.isEmpty()) {
            throw new ProductException(
                ProductErrMsg.PRODUCT_NOT_FOUND,
                String.format("Product (%s) not found", name)
            );
        }

        List<ProductVariantDetailDTO> variants = ProductVariantDetailMapper.toDTO(
            rows,
            uploadFileProps.image().defaultImage(),
            uploadFileProps.image().imageSeparator()
        );
        return ProductDetailMapper.toDTO(rows.getFirst(), variants);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return cacheService.getOrLoad(
            cacheProps.allKey(),
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> productRepo
                .findAll()
                .stream()
                .map(product -> ProductMapper.toDTO(product, uploadFileProps.image().defaultImage()))
                .toList()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public ProductDetailDTO getProductDetail(String name) {
        return cacheService.getOrLoad(
            cacheProps.detailKey() + name,
            Duration.ofMillis(cacheProps.all().cacheDuration()),
            new TypeReference<>() {
            },
            () -> loadProductDetail(name)
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getPagedProducts(Pageable pageable) {
        return productRepo
            .findAllByVariantsIsNotEmpty(pageable)
            .map(ele -> ProductMapper.toDTO(ele, uploadFileProps.image().defaultImage()));
    }


    @Override
    @Transactional(readOnly = true)
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
            .map(ele -> ProductMapper.toDTO(ele, uploadFileProps.image().defaultImage()));
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchByName(String name) {
        return productRepo
            .findByNameContainingIgnoreCase(name)
            .stream()
            .map(p -> ProductMapper.toDTO(p, uploadFileProps.image().defaultImage()))
            .toList();
    }


    @Override
    @Transactional
    public String insertProduct(InsertProductRequest req) {
        BrandEntity brand = brandRepo
            .findByNameIgnoreCase(req.getBrandName())
            .orElseThrow(() -> new BrandException(BrandErrMsg.BRAND_NOT_FOUND));

        if (productRepo.existsByNameAndBrand_Id(req.getName(), brand.getId()))
            throw new ProductException(ProductErrMsg.PRODUCT_EXISTED_BY_BRAND);

        ProductEntity saved = productRepo.save(ProductMapper.toEntity(req, brand));

        Set<CategoryEntity> categories = req.getCategoryNames()
            .stream()
            .map(ele -> categoryRepo
                .findByNameIgnoringCase(ele)
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
                .findByNameIgnoringCase(ele)
                .orElseThrow(() -> new TagException(TagErrMsg.TAG_NOT_FOUND))
            )
            .collect(Collectors.toSet());

        productTagRepo.saveAll(
            tags.stream()
                .map(c -> new ProductTagEntity(saved, c))
                .toList()
        );

        cacheService.evictAfterCommit(cacheProps.allKey());
        return saved.getName();
    }


    @Override
    @Transactional
    public void insertVariant(InsertVariantRequest req) {
        ProductEntity product = productRepo
            .findById(req.getIdProduct())
            .orElseThrow(() -> new ProductException(ProductErrMsg.PRODUCT_NOT_FOUND));

        ColorEntity color = colorRepo
            .findByNameIgnoreCase(req.getColorName())
            .orElseThrow(() -> new ColorException(ColorErrMsg.COLOR_NOT_FOUND));

        SizeEntity size = sizeRepo
            .findByNameIgnoreCase(req.getSizeName())
            .orElseThrow(() -> new SizeException(SizeErrMsg.SIZE_NOT_FOUND));

        if (productVariantRepo.existsByProductIdAndColorIdAndSizeId(product.getId(), color.getId(), size.getId()))
            throw new ProductException(ProductErrMsg.VARIANT_EXISTED);

        List<String> uploadedURIs = new ArrayList<>();
        for (MultipartFile file : req.getFiles()) {
            fileService.save(
                file,
                buildVariantImageSavePath(
                    uploadFileProps.image().path(),
                    req.getBrandName(),
                    product.getName()
                )
            );
            uploadedURIs.add(file.getOriginalFilename());
        }

        ProductVariantEntity variant = new ProductVariantEntity();
        variant.setProduct(product);
        variant.setColor(color);
        variant.setSize(size);
        variant.setQuantity(req.getQuantity());
        variant.setPrice(req.getPrice());
        variant.setImages(String.join(uploadFileProps.image().imageSeparator(), uploadedURIs));

        productVariantRepo.save(variant);

        cacheService.evictAfterCommit(
            cacheProps.allKey(),
            cacheProps.detailKey() + product.getName()
        );
    }
}
