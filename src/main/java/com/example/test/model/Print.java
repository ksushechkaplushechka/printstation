package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "prints")
public class Print {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "print_id")
    private Long id;

    @Column(name = "print_name")
    @NotBlank(message = "Название печати не должно быть пустым")
    @Size(max = 50, message = "Название печати не должно превышать 50 символов")
    private String name;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
