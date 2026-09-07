package com.ndt.capstone.entity;

import jakarta.persistence.*;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "color")
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,  unique = true, length = 20)
    private String name;
}
