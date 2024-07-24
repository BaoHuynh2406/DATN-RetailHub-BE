package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Categories
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;

    @OneToMany(mappedBy = "categorie")
    private List<Products> product;
}
