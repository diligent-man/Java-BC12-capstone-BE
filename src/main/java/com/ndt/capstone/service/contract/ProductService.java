package com.ndt.capstone.service.contract;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.payload.request.InsertProductRequest;


public interface ProductService {
    List<ProductDTO> getAll();


    Page<ProductDTO> getPagedProducts(Pageable pageable);


    Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize);


    void insertProduct(InsertProductRequest productRequester);

}
