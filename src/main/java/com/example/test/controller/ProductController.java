package com.example.test.controller;

import com.example.test.model.Product;
import com.example.test.repositories.ProductRepository;
import com.example.test.repositories.ProductGroupRepository;
import com.example.test.repositories.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductController(ProductRepository productRepository,
                             SupplierRepository supplierRepository,
                             ProductGroupRepository productGroupRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productGroupRepository = productGroupRepository;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            return "products/show";
        } else {
            model.addAttribute("error", "Продукт не найден");
            return "products/index";
        }
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("productGroups", productGroupRepository.findAll());
        return "products/new";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            model.addAttribute("productGroups", productGroupRepository.findAll());
            return "products/new";
        }

        if (product.getSupplier() == null || product.getProductGroup() == null) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            model.addAttribute("productGroups", productGroupRepository.findAll());
            model.addAttribute("errorMessage", "Необходимо выбрать поставщика и группу продукта.");
            return "products/new";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            model.addAttribute("suppliers", supplierRepository.findAll());
            model.addAttribute("productGroups", productGroupRepository.findAll());
            return "products/edit";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") Product updatedProduct,
                                BindingResult result,
                                @RequestParam(required = false) Long supplierId,
                                @RequestParam(required = false) Long productGroupId,
                                Model model) {
        if (supplierId == null || productGroupId == null) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            model.addAttribute("productGroups", productGroupRepository.findAll());
            model.addAttribute("errorMessage", "Необходимо выбрать поставщика и группу продукта.");
            return "products/edit";
        }

        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            model.addAttribute("productGroups", productGroupRepository.findAll());
            model.addAttribute("errorMessage", "Цена покупки должна быть положительным числом");
            return "products/edit";
        }

        updatedProduct.setId(id);
        updatedProduct.setSupplier(supplierRepository.findById(supplierId).orElse(null));
        updatedProduct.setProductGroup(productGroupRepository.findById(productGroupId).orElse(null));
        productRepository.save(updatedProduct);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("products", productRepository.findByNameContainingIgnoreCase(query));
        return "products/index";
    }
}
