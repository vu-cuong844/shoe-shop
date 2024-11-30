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
@Table(name = "shipper_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    // Đánh dấu quan hệ ManyToOne và chỉ định tên cột khóa ngoại
    @ManyToOne
    @JoinColumn(name = "ID_shipper", referencedColumnName = "ID")
    private Shipper shipper;

    @ManyToOne
    @JoinColumn(name = "ID_order", referencedColumnName = "ID")
    private Order order;

    // Các trường thời gian
    @Column(name = "receive_date")
    private LocalDateTime receive_date;

    @Column(name = "delivery_date")
    private LocalDateTime delivery_date;

    @Column(name = "status")
    private String status;

    public ShipperOrder(Shipper shipper, Order order, LocalDateTime receive_date, String status) {
        this.shipper = shipper;
        this.order = order;
        this.receive_date = receive_date;
        this.status = status;
    }
}
