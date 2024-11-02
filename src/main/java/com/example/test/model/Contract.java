package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @Column(name = "contract_number")
    @NotBlank(message = "Номер контракта не должен быть пустым")
    @Size(max = 20, message = "Номер контракта не должен превышать 20 символов")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Номер контракта должен содержать только заглавные буквы, цифры и дефисы")
    private String contractNumber;

    @Column(name = "contract_date")
    @NotNull(message = "Дата контракта не должна быть пустой")
    @PastOrPresent(message = "Дата контракта должна быть в прошлом или сегодня")
    private LocalDate contractDate;

    @Column(name = "contract_sum")
    @NotNull(message = "Сумма контракта не должна быть пустой")
    @Positive(message = "Сумма контракта должна быть положительной")
    @DecimalMin(value = "0.01", message = "Сумма контракта должна быть больше 0")
    private Double contractSum;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
    }
}
