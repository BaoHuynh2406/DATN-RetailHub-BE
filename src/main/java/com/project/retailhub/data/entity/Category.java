package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition = "INT")
    private int categoryId;

    @Nationalized
    @Column(name = "category_name", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String categoryName;

    @Column(name = "is_delete", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean isDelete;

    // Khóa ngoại
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
