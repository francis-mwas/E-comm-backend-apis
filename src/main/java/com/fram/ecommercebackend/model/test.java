package com.fram.ecommercebackend.model;

import javax.persistence.*;

@Entity
@Table
public class test {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ïd", nullable = false)
        Long id;
    }
