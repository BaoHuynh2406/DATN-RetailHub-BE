package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;
    private boolean isDelete;

    // Khóa ngoại
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
