package com.example.test.controller;

import com.example.test.model.Client;
import com.example.test.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            model.addAttribute("client", clientOpt.get());
            return "clients/show";
        } else {
            model.addAttribute("error", "Клиент не найден");
            return "clients/index";
        }
    }

    @GetMapping("/new")
    public String newClient(Model model) {
        model.addAttribute("client", new Client());
        return "clients/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("client") Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clients/new";
        }
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            model.addAttribute("client", clientOpt.get());
            return "clients/edit";
        } else {
            return "redirect:/clients";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            client.setId(id);
            return "clients/edit";
        }
        client.setId(id);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String name, Model model) {
        model.addAttribute("clients", clientRepository.findByFullNameContainingIgnoreCase(name));
        return "clients/index";
    }

    @GetMapping("/reset")
    public String resetSearch(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "clients/index";
    }
}
