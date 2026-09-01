package com.ndt.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String information;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private BrandEntity brand;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    // khi đi làm ngta thường map ngược lại ( không map ngược lại vẫn được nhma đa phần sẽ phải map ngược lại)
    @OneToMany(mappedBy = "product")
    // ở đấy map ngược lại bảng variant để khi insert ở bản product nó sẽ tự động truy vấn đến variant và thêm vào bảng variant
    private List<VariantProductEntity> variantProducts;

}
