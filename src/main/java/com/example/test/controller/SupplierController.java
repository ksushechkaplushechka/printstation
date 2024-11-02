package com.example.test.controller;

import com.example.test.model.Supplier;
import com.example.test.repositories.SupplierRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public String getAllSuppliers(Model model) {
        List<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        return "suppliers/index";
    }

    @GetMapping("/{id}")
    public String getSupplierById(@PathVariable Long id, Model model) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isPresent()) {
            model.addAttribute("supplier", supplierOpt.get());
            return "suppliers/show";
        } else {
            model.addAttribute("error", "Поставщик не найден");
            return "suppliers/index";
        }
    }

    @GetMapping("/new")
    public String newSupplier(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/new";
    }

    @PostMapping
    public String createSupplier(@ModelAttribute("supplier") Supplier supplier, Model model) {
        try {
            supplierRepository.save(supplier);
            return "redirect:/suppliers";
        } catch (ConstraintViolationException ex) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errorMessages.append(violation.getMessage()).append("<br>");
            }
            model.addAttribute("error", errorMessages.toString());
            model.addAttribute("supplier", supplier);
            return "suppliers/new";
        }
    }
    @GetMapping("/{id}/edit")
    public String editSupplier(@PathVariable Long id, Model model) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(id);
        if (supplierOpt.isPresent()) {
            model.addAttribute("supplier", supplierOpt.get());
            return "suppliers/edit";
        } else {
            return "redirect:/suppliers";
        }
    }

    @PostMapping("/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute("supplier") Supplier updatedSupplier, Model model) {
        try {
            updatedSupplier.setId(id);
            supplierRepository.save(updatedSupplier);
            return "redirect:/suppliers";
        } catch (ConstraintViolationException ex) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errorMessages.append(violation.getMessage()).append("<br>");
            }
            model.addAttribute("error", errorMessages.toString());
            model.addAttribute("supplier", updatedSupplier);
            return "suppliers/edit";
        } catch (Exception ex) {
            model.addAttribute("error", "Произошла ошибка при обновлении поставщика: проперьте название (не должно быть пустым, не должно превышать 100 символов, должно содержать только буквы и пробелы), адрес (не должен быть пустым, не должен превышать 200 символов, должен содержать хотя бы одну цифру и не включать знаков препинания), номер телефона (не должен быть пустым, должен начинаться с 8 и содержать 11 цифр)");
            model.addAttribute("supplier", updatedSupplier);
            return "suppliers/edit";
        }
    }


    @PostMapping("/{id}/delete")
    public String deleteSupplier(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return "redirect:/suppliers";
    }

    @GetMapping("/search")
    public String searchSuppliers(@RequestParam String query, Model model) {
        List<Supplier> suppliers = supplierRepository.findByNameContaining(query);
        model.addAttribute("suppliers", suppliers);
        return "suppliers/index";
    }

}
