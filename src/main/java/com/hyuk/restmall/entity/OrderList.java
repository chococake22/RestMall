package com.hyuk.restmall.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "TB_ORDER_LIST")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String status;

    @Column(length = 20)
    private String sender;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String receiver;

    @Column(length = 10)
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 100)
    private String createdUser;



    @CreatedDate
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String updatedUser;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
