package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    @NotBlank(message = "Название продукта не должно быть пустым")
    @Size(max = 100, message = "Название продукта не должно превышать 100 символов")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Описание продукта не должно превышать 255 символов")
    private String description;

    @Column(name = "purchase_price")
    @NotNull(message = "Цена покупки должна быть указана")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена покупки должна быть положительным числом")
    private Double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ProductGroup productGroup;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
