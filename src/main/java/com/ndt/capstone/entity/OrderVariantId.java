package com.ndt.capstone.entity;

import java.io.Serializable;


import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderVariantId implements Serializable {
    private Long order;

    private Long variant;
}
