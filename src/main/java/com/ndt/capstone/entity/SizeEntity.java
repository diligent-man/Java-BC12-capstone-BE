package com.ndt.capstone.entity;

import jakarta.persistence.*;


import lombok.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "size")
public class SizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    private String name;
}
