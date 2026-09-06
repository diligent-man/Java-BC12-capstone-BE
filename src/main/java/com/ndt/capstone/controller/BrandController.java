package com.ndt.capstone.controller;

import com.ndt.capstone.payload.request.GetProductRequest;
import com.ndt.capstone.payload.request.InsertProductRequest;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.response.PageResponse;
import com.ndt.capstone.service.contract.ProductService;
import com.ndt.capstone.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final ProductService productService;


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
        pageable = PageableUtils.withDefaultSort(
            pageable,
            Sort.by(
                Sort.Order.asc("price"),
                Sort.Order.asc("name")

            )
        );

        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(PageResponse.from(productService.getPagedProducts(pageable)))
                .build()
        );
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(GetProductRequest request) {
        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .message("search product success")
            .data(productService.searchProductByName(
                request.getKeyword(),
                request.getPageNumber(),
                request.getPageSize()))
            .build();

        return ResponseEntity.ok(baseResponse);
    }


    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(InsertProductRequest request) {
        productService.insertProduct(request);

        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .message("insert created")
            .build();

        return ResponseEntity.ok(baseResponse);
    }
}
