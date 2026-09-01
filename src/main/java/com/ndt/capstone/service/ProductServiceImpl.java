package com.ndt.capstone.service;

import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.entity.*;
import com.ndt.capstone.mapper.ProductMapper;
import com.ndt.capstone.payload.request.InsertProductRequest;
import com.ndt.capstone.repository.ProductRepository;
import com.ndt.capstone.repository.VariantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    VariantRepository variantRepository;

    @Autowired
    private FileService fileServices;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // Thêm 2 dòng này
    @PersistenceContext
    private EntityManager entityManager;

    private static final String PRODUCT_CACHE_KEY = "products:all";


    @Override
    @Transactional
    // biến nguyên hàm được đặt trên thành 1 giao dịch, nếu cả hàm chạy thành công thì mới thực hiện truy vấn tới database
    public void insertProduct(InsertProductRequest productRequester) {
        fileServices.save(productRequester.getFile()); // lưu hình

        ProductEntity product = new ProductEntity();
        product.setName(productRequester.getName());
        product.setDescription(productRequester.getDescription());
        product.setPrice(productRequester.getPrice());

        BrandEntity brand = entityManager.getReference(BrandEntity.class, productRequester.getIdBrand());
        product.setBrand(brand);
        ProductEntity productInserted = productRepository.save(product); // luu bang product
        // jpa mặc định sẽ trả ra dòng dữ liệu vừa insert để có thể tiếp tục lấy id product để truy vấn vào bảng variant, nếu làm chay phải lấy truy vấn lấy id max

        ColorEntity color = entityManager.getReference(ColorEntity.class, productRequester.getIdColor());
        SizeEntity size = entityManager.getReference(SizeEntity.class, productRequester.getIdSize());

        VariantProductEntity variantProduct = new VariantProductEntity();
        variantProduct.setProduct(productInserted);
        variantProduct.setColor(color);
        variantProduct.setIdSize(size);
        variantProduct.setImages(productRequester.getFile().getOriginalFilename()); // lấy tên hình để lưu vào bảng variant

        variantRepository.save(variantProduct); // luu bang varint, phai luu ca 2 bang cung luc
    }


    @Override // su dung paging, paging cung la 1 co che cache
    public Page<ProductDTO> getAllProductByPage(int pageNumber, int pageSize) { // bản chất của kiểu page là list
        Pageable page = PageRequest.of(pageNumber, pageSize);
        // cách 1
        // return productRepository.findAll(page).map(ProductMapper::toProductDTO);
        // cách 2
        return productRepository.findAll(page).map(product -> ProductMapper.toProductDTO(product));
    }


    // sử dụng mem-cache
    //    @Cacheable("product") // đặt tên cho cache là product
    //    @Override
    //    public List<ProductDTO> getAllProduct() {
    //        System.out.println("Kiemtra product");
    //        return productRepository.findAll()
    //                .stream()
    //                .map(ProductMapper::toProductDTO)
    //                .toList();
    //    }


    @Override
    public List<ProductDTO> getAllProduct() {

        try {

            // 1. Đọc cache
            String cache = redisTemplate.opsForValue().get(PRODUCT_CACHE_KEY);

            if (cache != null && !cache.isBlank()) {
                System.out.println("Load Product From Redis");

                return objectMapper.readValue(
                    cache,
                    new TypeReference<List<ProductDTO>>() {
                    });
            }

            // 2. Không có cache -> Query DB
            System.out.println("Load Product From Database");

            List<ProductDTO> products = productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductDTO)
                .toList();

            // 3. Lưu cache 10 phút
            redisTemplate.opsForValue().set(
                PRODUCT_CACHE_KEY,
                objectMapper.writeValueAsString(products),
                Duration.ofMinutes(10));

            return products;

        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }


    @Override
    public Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCase(keyword, page)
            .map(product -> ProductMapper.toProductDTO(product));
    }

}
