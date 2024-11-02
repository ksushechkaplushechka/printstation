package com.example.test.controller;

import com.example.test.model.Employee;
import com.example.test.model.Role;
import com.example.test.repositories.EmployeeRepository;
import com.example.test.repositories.RoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees/index";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            return "employees/show";
        } else {
            model.addAttribute("error", "Сотрудник не найден");
            return "employees/index";
        }
    }

    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("roles", roleRepository.findAll());
        return "employees/new";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute("employee") Employee employee, @RequestParam("roleId") Long roleId, Model model) {
        try {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Некорректный ID роли: " + roleId));
            employee.setRole(role);
            employeeRepository.save(employee);
            return "redirect:/employees";
        } catch (ConstraintViolationException e) {
            model.addAttribute("errorMessages", e.getConstraintViolations());
            model.addAttribute("roles", roleRepository.findAll());
            return "employees/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String editEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            model.addAttribute("roles", roleRepository.findAll());
            return "employees/edit";
        } else {
            return "redirect:/employees";
        }
    }

    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute("employee") Employee updatedEmployee, @RequestParam("roleId") Long roleId) {
        updatedEmployee.setId(id);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Некорректный ID роли: " + roleId));
        updatedEmployee.setRole(role);
        employeeRepository.save(updatedEmployee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String name, Model model) {
        model.addAttribute("employees", employeeRepository.findByNameContainingIgnoreCase(name));
        return "employees/index";
    }
}
