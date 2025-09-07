package com.hyuk.restmall.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "TB_DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String addressName;

    @Column(length = 255)
    private String address;

    @Column(length = 10)
    private String zipCode;

    @Column(length = 100)
    private String createdUser;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String updatedUser;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
