package com.ndt.capstone.service.contract;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.ndt.capstone.payload.req.product.*;

import com.ndt.capstone.dto.product.ProductDTO;
import com.ndt.capstone.dto.product.ProductDetailDTO;


public interface ProductService {
    List<ProductDTO> getAll();


    ProductDetailDTO getProductDetail(String name);


    Page<ProductDTO> getPagedProducts(Pageable pageable);


    Page<ProductDTO> filterProduct(ProductFilterRequest req, Pageable pageable);


    List<ProductDTO> searchByName(String name);


    String insertProduct(InsertProductRequest req);


    void insertVariant(InsertVariantRequest req);
}
