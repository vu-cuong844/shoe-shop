package com.example.Project1.model;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Category")
    private String category;

    @Column(name = "Purchare_price")
    private double purchare_price;

    @Column(name = "Sale_price")
    private double sale_price;

    @Column(name = "Image_url")
    private String image_url;

    @Column(name = "Description")
    private String description;

    @Column(name = "Status")
    private int status;

    @Transient
    private MultipartFile image;

}
