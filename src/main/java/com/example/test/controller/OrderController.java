package com.example.test.controller;

import com.example.test.model.Order;
import com.example.test.model.Client;
import com.example.test.model.Employee;
import com.example.test.model.OrderStatus;
import com.example.test.model.Service;
import com.example.test.model.Contract; // Импортируйте модель контракта
import com.example.test.repositories.OrderRepository;
import com.example.test.repositories.ClientRepository;
import com.example.test.repositories.EmployeeRepository;
import com.example.test.repositories.OrderStatusRepository;
import com.example.test.repositories.ServiceRepository;
import com.example.test.repositories.ContractRepository; // Импортируйте репозиторий контракта
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                           ClientRepository clientRepository,
                           ServiceRepository serviceRepository,
                           EmployeeRepository employeeRepository,
                           OrderStatusRepository orderStatusRepository,
                           ContractRepository contractRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.employeeRepository = employeeRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.contractRepository = contractRepository;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            return "orders/show";
        } else {
            model.addAttribute("error", "Заказ не найден");
            return "orders/index";
        }
    }

    @GetMapping("/new")
    public String newOrder(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("statuses", orderStatusRepository.findAll());
        model.addAttribute("contracts", contractRepository.findAll());
        return "orders/new"; // форма создания нового заказа
    }

    @PostMapping
    public String createOrder(@ModelAttribute("order") Order order) {
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("services", serviceRepository.findAll());
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("statuses", orderStatusRepository.findAll());
            model.addAttribute("contracts", contractRepository.findAll());
            return "orders/edit";
        } else {
            return "redirect:/orders";
        }
    }

    @PostMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute("order") Order updatedOrder) {
        updatedOrder.setId(id);
        orderRepository.save(updatedOrder);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String contractNumber, Model model) {
        // Замените findByContractNumberContainingIgnoreCase на ваш метод поиска
        model.addAttribute("orders", orderRepository.findByContractNumberContainingIgnoreCase(contractNumber));
        return "orders/index";
    }
}
