package com.example.test.controller;

import com.example.test.model.OrderStatus;
import com.example.test.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order-status")
public class OrderStatusController {

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusController(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @GetMapping
    public String getAllOrderStatus(Model model) {
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        model.addAttribute("orderStatusList", orderStatusList);
        return "order-status/index";
    }

    @GetMapping("/{id}")
    public String getOrderStatusById(@PathVariable Long id, Model model) {
        Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(id);
        if (orderStatusOpt.isPresent()) {
            model.addAttribute("orderStatus", orderStatusOpt.get());
            return "order-status/show";
        } else {
            model.addAttribute("error", "Статус заказа не найден");
            return "order-status/index";
        }
    }

    @GetMapping("/new")
    public String newOrderStatus(Model model) {
        model.addAttribute("orderStatus", new OrderStatus());
        return "order-status/new";
    }

    @PostMapping
    public String createOrderStatus(@ModelAttribute("orderStatus") OrderStatus orderStatus) {
        orderStatusRepository.save(orderStatus);
        return "redirect:/order-status";
    }

    @GetMapping("/{id}/edit")
    public String editOrderStatus(@PathVariable Long id, Model model) {
        Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(id);
        if (orderStatusOpt.isPresent()) {
            model.addAttribute("orderStatus", orderStatusOpt.get());
            return "order-status/edit";
        } else {
            return "redirect:/order-status";
        }
    }

    @PostMapping("/{id}")
    public String updateOrderStatus(@PathVariable Long id, @ModelAttribute("orderStatus") OrderStatus updatedOrderStatus) {
        updatedOrderStatus.setId(id);
        orderStatusRepository.save(updatedOrderStatus);
        return "redirect:/order-status";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrderStatus(@PathVariable Long id) {
        orderStatusRepository.deleteById(id);
        return "redirect:/order-status";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("orderStatusList", orderStatusRepository.findByStatusNameContainingIgnoreCase(query));
        return "order-status/index";
    }

}
