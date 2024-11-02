package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name")
    @NotBlank(message = "Имя сотрудника не должно быть пустым")
    @Size(max = 100, message = "Имя сотрудника не должно превышать 100 символов")
    @Pattern(regexp = "^(?=.*\\s)([А-Яа-яЁёA-Za-z]{2,}\\s+[А-Яа-яЁёA-Za-z]{2,})$",
            message = "Имя сотрудника должно состоять минимум из двух слов, каждое из которых содержит не менее двух букв, и содержать только буквы и пробелы")
    private String name;

    @Column(name = "inn")
    @NotBlank(message = "ИНН не может быть пустым")
    @Pattern(regexp = "^\\d{10,12}$", message = "ИНН должен содержать от 10 до 12 цифр")
    private String inn;

    @Column(name = "address")
    @NotBlank(message = "Адрес не должен быть пустым")
    @Size(max = 100, message = "Адрес не должен превышать 100 символов")
    @Pattern(regexp = "^[^.,?!;:]*\\d+[^.,?!;:]*$", message = "Адрес должен содержать хотя бы одно число и не должен содержать знаков препинания")
    private String address;

    @Column(name = "phone")
    @NotBlank(message = "Телефон не должен быть пустым")
    @Pattern(regexp = "^(8|9)\\d{10}$", message = "Телефон должен начинаться с '8' или '9' и содержать 11 цифр.")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Роль не должна быть пустой")
    private Role role;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;

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

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
