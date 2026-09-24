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
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false)
    private RoleEntity role;

    @Column(nullable = false, length = 20, insertable = false)
    private String status;
}
