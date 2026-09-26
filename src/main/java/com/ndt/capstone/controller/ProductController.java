package com.ndt.capstone.controller;

import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.data.web.PageableDefault;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;


import static com.ndt.capstone.utils.PageableUtils.withDefaultSort;

import com.ndt.capstone.service.contract.ProductService;

import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.response.PageResponse;

import com.ndt.capstone.payload.request.product.ProductFilterRequest;
import com.ndt.capstone.payload.request.product.InsertProductRequest;
import com.ndt.capstone.payload.request.product.InsertVariantRequest;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final Sort defaultProductSort = Sort.by(
        Sort.Order.asc("price"),
        Sort.Order.asc("name")
    );


    @GetMapping
    public ResponseEntity<ApiResponse> getProducts() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(productService.getAll())
                .build()
        );
    }


    @GetMapping("/paging")
    public ResponseEntity<ApiResponse> getPagedProducts(
        @PageableDefault(size = 5, direction = Sort.Direction.ASC)
        Pageable pageable
    ) {
        pageable = withDefaultSort(defaultProductSort, pageable);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(PageResponse.from(productService.getPagedProducts(pageable)))
                .build()
        );
    }


    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> filterProduct(
        @Valid @ModelAttribute
        ProductFilterRequest req,
        Pageable pageable
    ) {
        pageable = withDefaultSort(defaultProductSort, pageable);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(PageResponse.from(productService.filterProduct(req, pageable)))
                .build()
        );
    }


    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> getProductDetail(
        @PathVariable String name
    ) {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(productService.getProductDetail(name))
                .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(productService.searchByName(name))
                        .build()
        );
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<ApiResponse> insertProduct(@RequestBody InsertProductRequest request) {
        Long productId = productService.insertProduct(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Product created successfully")
                        .data(productId)   // trả về id để FE dùng ở bước 2
                        .build()
        );
    }

    @PostMapping(value = "/variant/insert", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> insertVariant(@ModelAttribute InsertVariantRequest request) {
        productService.insertVariant(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Variant added successfully")
                        .build()
        );
    }
}
