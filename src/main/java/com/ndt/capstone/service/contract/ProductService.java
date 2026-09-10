package com.ndt.capstone.service.contract;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.payload.request.product.*;


public interface ProductService {
    List<ProductDTO> getAll();


    Page<ProductDTO> getPagedProducts(Pageable pageable);


    Page<ProductDTO> filterProduct(ProductFilterRequest request, Pageable pageable);


    void insertProduct(InsertProductRequest productRequester);
}
