package com.example.test.controller;

import com.example.test.model.ProductGroup;
import com.example.test.repositories.ProductGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/group-products")
public class GroupProductController {

    private final ProductGroupRepository groupProductRepository;

    @Autowired
    public GroupProductController(ProductGroupRepository groupProductRepository) {
        this.groupProductRepository = groupProductRepository;
    }

    @GetMapping
    public String getAllGroupProducts(Model model) {
        model.addAttribute("groupProducts", groupProductRepository.findAll());
        return "group-products/index"; // возвращаем представление
    }

    @GetMapping("/{id}")
    public String getGroupProductById(@PathVariable Long id, Model model) {
        Optional<ProductGroup> groupProductOpt = groupProductRepository.findById(id);
        if (groupProductOpt.isPresent()) {
            model.addAttribute("groupProduct", groupProductOpt.get());
            return "group-products/show"; // возвращаем представление
        } else {
            model.addAttribute("error", "Группа продуктов не найдена");
            return "group-products/index"; // возвращаем представление со списком
        }
    }

    @GetMapping("/new")
    public String newGroupProduct(Model model) {
        model.addAttribute("groupProduct", new ProductGroup());
        return "group-products/new"; // форма создания
    }

    @PostMapping
    public String createGroupProduct(@ModelAttribute("groupProduct") ProductGroup groupProduct) {
        groupProductRepository.save(groupProduct);
        return "redirect:/group-products"; // перенаправление после создания
    }

    @GetMapping("/{id}/edit")
    public String editGroupProduct(@PathVariable Long id, Model model) {
        Optional<ProductGroup> groupProductOpt = groupProductRepository.findById(id);
        if (groupProductOpt.isPresent()) {
            model.addAttribute("groupProduct", groupProductOpt.get());
            return "group-products/edit"; // форма редактирования
        } else {
            return "redirect:/group-products"; // перенаправление на список
        }
    }

    @PostMapping("/{id}")
    public String updateGroupProduct(@PathVariable Long id, @ModelAttribute("groupProduct") ProductGroup updatedGroupProduct) {
        updatedGroupProduct.setId(id);
        groupProductRepository.save(updatedGroupProduct);
        return "redirect:/group-products"; // перенаправление после обновления
    }

    @PostMapping("/{id}/delete")
    public String deleteGroupProduct(@PathVariable Long id) {
        groupProductRepository.deleteById(id);
        return "redirect:/group-products"; // перенаправление после удаления
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String name, Model model) {
        model.addAttribute("groupProducts", groupProductRepository.findByGroupNameContainingIgnoreCase(name));
        return "group-products/index"; // возвращаем представление со списком
    }

}
