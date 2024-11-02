package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private Long id;

    @Column(name = "machine_name")
    @NotBlank(message = "Наименование машины не должно быть пустым")
    @Size(max = 50, message = "Наименование машины не должно превышать 50 символов")
    private String name;

    @Column(name = "description")
    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;

    @OneToMany(mappedBy = "machine")
    private List<Print> prints;

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

    public List<Print> getPrints() {
        return prints;
    }

    public void setPrints(List<Print> prints) {
        this.prints = prints;
    }
}
