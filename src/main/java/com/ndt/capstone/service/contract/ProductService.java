package com.ndt.capstone.service.contract;

import java.util.List;


import com.ndt.capstone.dto.product.ProductDetailDTO;
import com.ndt.capstone.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.ndt.capstone.dto.product.ProductDTO;
import com.ndt.capstone.payload.request.product.*;


public interface ProductService {
    List<ProductDTO> getAll();


    ProductDetailDTO getProductDetail(String name);


    Page<ProductDTO> getPagedProducts(Pageable pageable);


    Page<ProductDTO> filterProduct(ProductFilterRequest request, Pageable pageable);

    List<ProductDTO> searchByName(String name);

    Long insertProduct(InsertProductRequest productRequest);

    void insertVariant(InsertVariantRequest variantRequest);
}
