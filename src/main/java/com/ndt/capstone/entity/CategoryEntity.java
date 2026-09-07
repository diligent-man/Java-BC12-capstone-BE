package com.ndt.capstone.entity;

import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @Column(unique = true, nullable = false, length = 50)
    private String name;
}
