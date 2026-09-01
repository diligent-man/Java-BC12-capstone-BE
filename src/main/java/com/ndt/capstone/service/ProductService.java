package com.ndt.capstone.service;

import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.payload.request.InsertProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    void insertProduct(InsertProductRequest productRequester);
    Page<ProductDTO> getAllProductByPage(int pageNumber, int pageSize);
    List<ProductDTO> getAllProduct();
    Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize);
}
