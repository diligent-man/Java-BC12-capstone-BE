package com.ndt.capstone.entity;

import jakarta.persistence.*;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "brand")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    private String name;
}
