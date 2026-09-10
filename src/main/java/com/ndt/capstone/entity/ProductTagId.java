package com.ndt.capstone.entity;

import java.io.Serializable;


import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductTagId implements Serializable {
    private Long product;

    private Integer tag;
}
