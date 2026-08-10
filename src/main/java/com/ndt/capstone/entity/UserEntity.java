package com.ndt.capstone.entity;

import jakarta.persistence.*;


import lombok.*;


@Setter
@Getter
@ToString
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String fullName;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
