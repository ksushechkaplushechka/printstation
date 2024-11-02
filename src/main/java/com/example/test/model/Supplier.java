package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Column(name = "supplier_name")
    @NotBlank(message = "Название поставщика не должно быть пустым")
    @Size(max = 100, message = "Название поставщика не должно превышать 100 символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$", message = "Название должно содержать только буквы и пробелы")
    private String name;

    @Column(name = "address")
    @NotBlank(message = "Адрес не должен быть пустым")
    @Size(max = 200, message = "Адрес не должен превышать 200 символов")
    @Pattern(regexp = "^(?=.*\\d)[A-Za-zА-Яа-яЁё\\d\\s]+$", message = "Адрес должен содержать хотя бы одну цифру и не включать знаков препинания")
    private String address;

    @Column(name = "phone")
    @NotBlank(message = "Телефон не должен быть пустым")
    @Pattern(regexp = "^8\\d{10}$", message = "Номер телефона должен начинаться с 8 и содержать 11 цифр")
    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
