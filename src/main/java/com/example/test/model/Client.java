package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(max = 100, message = "ФИО не должно превышать 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁё\\s]+$", message = "ФИО не должно содержать цифр и знаков препинания")
    @Pattern(regexp = "^[А-Яа-яЁё]+\\s+[А-Яа-яЁё]+.*$", message = "ФИО должно содержать как минимум два слова")
    @Column(name = "client_name")
    private String fullName;

    @NotEmpty(message = "Адрес не должен быть пустым")
    @Pattern(regexp = ".*\\d+.*", message = "Адрес должен содержать хотя бы одну цифру")
    @Size(max = 200, message = "Адрес не должен превышать 200 символов")
    @Column(name = "address", nullable = false)
    private String address;

    @NotEmpty(message = "Телефон не должен быть пустым")
    @Pattern(regexp = "^8[0-9]{10}$", message = "Телефон должен начинаться с 8 и содержать 10 цифр без знаков препинания")
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
