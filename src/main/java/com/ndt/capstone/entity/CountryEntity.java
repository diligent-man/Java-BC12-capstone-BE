package com.ndt.capstone.entity;

import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 2)
    private String iso;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 80)
    private String niceName;

    @Column(length = 3)
    private String iso3;

    private Short numCode;

    @Column(nullable = false)
    private Integer phoneCode;
}
