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
@Table(name = "TB_CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 255)
    private String note;

    @Column(length = 100)
    private String createdUser;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String updatedUser;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
