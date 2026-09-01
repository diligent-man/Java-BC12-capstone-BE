package com.ndt.capstone.controller;

import com.ndt.capstone.payload.request.GetProductRequest;
import com.ndt.capstone.payload.request.InsertProductRequest;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/paging")
    public ResponseEntity<?> getAllProductByPage(GetProductRequest request) { // phương thuc GET khong duoc su dung @RequestBody
        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .status("get all product success")
            .data(productService.getAllProductByPage(request.getPageNumber(), request.getPageSize()))
            .build();

        return ResponseEntity.ok(baseResponse);
    }


    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .status("get all product success")
            .data(productService.getAllProduct())
            .build();

        return ResponseEntity.ok(baseResponse);
    }


    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(InsertProductRequest request) {
        productService.insertProduct(request);

        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .status("insert created")
            .build();

        return ResponseEntity.ok(baseResponse);
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(GetProductRequest request) {
        ApiResponse baseResponse = ApiResponse.builder()
            .code(HttpStatus.OK.toString())
            .status("search product success")
            .data(productService.searchProductByName(
                request.getKeyword(),
                request.getPageNumber(),
                request.getPageSize()))
            .build();

        return ResponseEntity.ok(baseResponse);
    }
}
