package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    @NotBlank(message = "Название роли не должно быть пустым")
    @Size(max = 50, message = "Название роли не должно превышать 50 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z0-9\\s]+$", message = "Название роли не должно содержать знаков препинания")

    private String name;

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
}
