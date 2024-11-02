package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Клиент обязателен для заказа")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotNull(message = "Услуга обязательна для заказа")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Сотрудник обязателен для заказа")
    private Employee employee;

    @Column(name = "contract_number")
    @Size(max = 20, message = "Номер контракта не должен превышать 20 символов")
    private String contractNumber;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    @NotNull(message = "Статус заказа обязателен")
    private OrderStatus status;

    @Column(name = "order_name")
    @NotBlank(message = "Название заказа не должно быть пустым")
    @Size(max = 50, message = "Название заказа не должно превышать 50 символов")
    private String orderName;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
