package com.example.test.controller;

import com.example.test.model.Machine;
import com.example.test.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/machines")
public class MachineController {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @GetMapping
    public String getAllMachines(Model model) {
        List<Machine> machines = machineRepository.findAll();
        model.addAttribute("machines", machines);
        return "machines/index";
    }

    @GetMapping("/{id}")
    public String getMachineById(@PathVariable Long id, Model model) {
        Optional<Machine> machineOpt = machineRepository.findById(id);
        if (machineOpt.isPresent()) {
            model.addAttribute("machine", machineOpt.get());
            return "machines/show"; // возвращаем представление детали машины
        } else {
            model.addAttribute("error", "Машина не найдена");
            return "machines/index";
        }
    }

    @GetMapping("/new")
    public String newMachine(Model model) {
        model.addAttribute("machine", new Machine());
        return "machines/new";
    }

    @PostMapping
    public String createMachine(@ModelAttribute("machine") Machine machine) {
        machineRepository.save(machine);
        return "redirect:/machines";
    }

    @GetMapping("/{id}/edit")
    public String editMachine(@PathVariable Long id, Model model) {
        Optional<Machine> machineOpt = machineRepository.findById(id);
        if (machineOpt.isPresent()) {
            model.addAttribute("machine", machineOpt.get());
            return "machines/edit";
        } else {
            return "redirect:/machines";
        }
    }

    @PostMapping("/{id}")
    public String updateMachine(@PathVariable Long id, @ModelAttribute("machine") Machine updatedMachine) {
        updatedMachine.setId(id);
        machineRepository.save(updatedMachine);
        return "redirect:/machines";
    }

    @PostMapping("/{id}/delete")
    public String deleteMachine(@PathVariable Long id) {
        machineRepository.deleteById(id);
        return "redirect:/machines";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String name, Model model) {
        model.addAttribute("machines", machineRepository.findByNameContainingIgnoreCase(name));
        return "machines/index";
    }
}
