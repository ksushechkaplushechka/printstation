package com.example.test.controller;

import com.example.test.model.Service;
import com.example.test.model.Supplier;
import com.example.test.model.Product;
import com.example.test.repositories.ServiceRepository;
import com.example.test.repositories.SupplierRepository;
import com.example.test.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ServiceController(ServiceRepository serviceRepository,
                             SupplierRepository supplierRepository,
                             ProductRepository productRepository) {
        this.serviceRepository = serviceRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/search")
    public String searchServices(@RequestParam String query, Model model) {
        List<Service> services = serviceRepository.findByNameContaining(query);
        model.addAttribute("services", services);
        model.addAttribute("searchQuery", query);
        return "services/index";
    }

    @GetMapping
    public String listServices(Model model) {
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "services/index";
    }

    @GetMapping("/{id}")
    public String showService(@PathVariable Long id, Model model) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + id));
        model.addAttribute("service", service);
        return "services/show";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        model.addAttribute("service", new Service());
        loadSuppliersAndProducts(model);
        return "services/new";
    }

    @GetMapping("/{id}/edit")
    public String editService(@PathVariable Long id, Model model) {
        try {
            Service service = serviceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Услуга с таким ID не найдена"));
            model.addAttribute("service", service);
            loadSuppliersAndProducts(model);
        } catch (Exception ex) {
            // Передача сообщения об ошибке
            model.addAttribute("errorMessage", "Ошибка при загрузке страницы. Пожалуйста, попробуйте снова позже.");
            model.addAttribute("service", new Service());
            model.addAttribute("suppliers", List.of());
            model.addAttribute("products", List.of());
        }
        return "services/edit";
    }

    @PostMapping
    public String createService(@ModelAttribute("service") @Valid Service service,
                                BindingResult result,
                                @RequestParam Long supplierId,
                                @RequestParam Long productId,
                                Model model) {
        if (result.hasErrors()) {
            loadSuppliersAndProducts(model);
            return "services/new";
        }

        service.setSupplier(supplierRepository.findById(supplierId).orElse(null));
        service.setProduct(productRepository.findById(productId).orElse(null));
        serviceRepository.save(service);
        return "redirect:/services";
    }

    @PostMapping("/{id}")
    public String updateService(@PathVariable Long id,
                                @ModelAttribute("service") @Valid Service updatedService,
                                BindingResult bindingResult,
                                @RequestParam Long supplierId,
                                @RequestParam Long productId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            loadSuppliersAndProducts(model);
            return "services/edit";
        }

        updatedService.setId(id);
        updatedService.setSupplier(supplierRepository.findById(supplierId).orElse(null));
        updatedService.setProduct(productRepository.findById(productId).orElse(null));

        try {
            serviceRepository.save(updatedService);
            return "redirect:/services";
        } catch (Exception ex) {
            String errorMessage = "Произошла ошибка: " + ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            loadSuppliersAndProducts(model);
            model.addAttribute("service", updatedService);

            return "services/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
        return "redirect:/services";
    }

    private void loadSuppliersAndProducts(Model model) {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<Product> products = productRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("products", products);
    }
}
