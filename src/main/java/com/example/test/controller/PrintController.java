package com.example.test.controller;

import com.example.test.model.Print;
import com.example.test.model.Machine; // Импортируем модель Machine
import com.example.test.repositories.PrintRepository;
import com.example.test.repositories.MachineRepository; // Импортируем репозиторий Machine
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prints")
public class PrintController {

    private final PrintRepository printRepository;
    private final MachineRepository machineRepository;

    @Autowired
    public PrintController(PrintRepository printRepository, MachineRepository machineRepository) {
        this.printRepository = printRepository;
        this.machineRepository = machineRepository;
    }

    @GetMapping
    public String getAllPrints(Model model) {
        List<Print> prints = printRepository.findAll();
        model.addAttribute("prints", prints);
        return "prints/index";
    }

    @GetMapping("/{id}")
    public String getPrintById(@PathVariable Long id, Model model) {
        Optional<Print> printOpt = printRepository.findById(id);
        if (printOpt.isPresent()) {
            model.addAttribute("print", printOpt.get());
            return "prints/show";
        } else {
            model.addAttribute("error", "Печать не найдена");
            return "prints/index";
        }
    }

    @GetMapping("/new")
    public String newPrint(Model model) {
        model.addAttribute("print", new Print());
        model.addAttribute("machines", machineRepository.findAll());
        return "prints/new";
    }

    @PostMapping
    public String createPrint(@RequestParam("machineId") Long machineId, @ModelAttribute("print") Print print) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Некорректный ID машины: " + machineId));
        print.setMachine(machine);
        printRepository.save(print);
        return "redirect:/prints";
    }

    @GetMapping("/{id}/edit")
    public String editPrint(@PathVariable Long id, Model model) {
        Optional<Print> printOpt = printRepository.findById(id);
        if (printOpt.isPresent()) {
            model.addAttribute("print", printOpt.get());
            model.addAttribute("machines", machineRepository.findAll());
            return "prints/edit";
        } else {
            return "redirect:/prints";
        }
    }

    @PostMapping("/{id}")
    public String updatePrint(@PathVariable Long id, @RequestParam("machineId") Long machineId, @ModelAttribute("print") Print updatedPrint) {
        updatedPrint.setId(id);
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Некорректный ID машины: " + machineId));
        updatedPrint.setMachine(machine);
        printRepository.save(updatedPrint);
        return "redirect:/prints";
    }

    @PostMapping("/{id}/delete")
    public String deletePrint(@PathVariable Long id) {
        printRepository.deleteById(id);
        return "redirect:/prints";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("prints", printRepository.findByNameContainingIgnoreCase(query));
        return "prints/index";
    }
}
