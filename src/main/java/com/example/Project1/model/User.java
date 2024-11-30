package com.example.Project1.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khach_hang")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Username")
    private String username; 

    @Column(name = "Role")
    private String role;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone_Number")
    private String phone;

    @Column(name = "Sex")
    private String sex;

    @Column(name = "Date_Of_Birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "Country")
    private String country;

    @Column(name = "Provine")
    private String provine;

    @Column(name = "District")
    private String district;

    @Column(name = "Street")
    private String street;

    @Column(name = "Address")
    private String address;

    // Getter và Setter nếu chưa được tạo bởi Lombok
}
