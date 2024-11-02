package com.example.test.controller;

import com.example.test.model.Role;
import com.example.test.repositories.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAllRoles(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Role> roles;
        if (search != null && !search.trim().isEmpty()) {
            roles = roleRepository.findByNameContainingIgnoreCase(search);
        } else {
            roles = roleRepository.findAll();
        }
        model.addAttribute("roles", roles);
        model.addAttribute("search", search);
        return "roles/index";
    }

    @GetMapping("/{id}")
    public String getRoleById(@PathVariable Long id, Model model) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isPresent()) {
            model.addAttribute("role", roleOpt.get());
            return "roles/show";
        } else {
            model.addAttribute("error", "Роль не найдена");
            return "roles/index";
        }
    }

    @GetMapping("/new")
    public String newRole(Model model) {
        model.addAttribute("role", new Role());
        return "roles/new";
    }

    @PostMapping
    public String createRole(@ModelAttribute("role") @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "roles/new";
        }
        roleRepository.save(role);
        return "redirect:/roles";
    }

    @PostMapping("/{id}")
    public String updateRole(@PathVariable Long id, @ModelAttribute("role") @Valid Role updatedRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "roles/edit";
        }
        updatedRole.setId(id);
        roleRepository.save(updatedRole);
        return "redirect:/roles";
    }

    @GetMapping("/{id}/edit")
    public String editRole(@PathVariable Long id, Model model) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isPresent()) {
            model.addAttribute("role", roleOpt.get());
            return "roles/edit";
        } else {
            return "redirect:/roles";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteRole(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return "redirect:/roles";
    }
}
