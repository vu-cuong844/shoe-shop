package com.example.Project1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    
    @ManyToOne()
    @JoinColumn(name = "ID_User", referencedColumnName = "ID")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "ID_Shoes", referencedColumnName = "ID")
    private Shoe shoes;

    @Column(name = "payment_method")  
    private String payment_method;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_date")
    private LocalDateTime order_date;

    @Column(name = "receive_date")
    private LocalDateTime receive_date;

    @Column(name = "status")
    private String status;

    @Column(name = "delivery_address")
    private String delivery_address;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "phonenumber")
    private String phonenumber;


}
