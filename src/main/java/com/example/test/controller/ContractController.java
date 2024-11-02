package com.example.test.controller;

import com.example.test.model.Contract;
import com.example.test.repositories.ContractRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractController(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("contracts", contractRepository.findAll());
        return "contracts/index";
    }

    @GetMapping("/{contractNumber}")
    public String show(@PathVariable("contractNumber") String contractNumber, Model model) {
        Optional<Contract> contractOpt = contractRepository.findById(contractNumber);
        if (contractOpt.isPresent()) {
            Contract contract = contractOpt.get();
            model.addAttribute("contract", contract);
            return "contracts/show";
        } else {
            model.addAttribute("error", "Контракт не найден");
            return "contracts/index";
        }
    }

    @GetMapping("/new")
    public String newContract(Model model) {
        model.addAttribute("contract", new Contract());
        return "contracts/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("contract") Contract contract, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "contracts/new";
        }
        contractRepository.save(contract);
        return "redirect:/contracts";
    }

    @PostMapping("/{contractNumber}")
    public String update(@PathVariable("contractNumber") String contractNumber, @Valid @ModelAttribute("contract") Contract updatedContract, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contracts/edit";
        }
        if (!contractNumber.equals(updatedContract.getContractNumber())) {
            contractRepository.deleteById(contractNumber);
        }
        contractRepository.save(updatedContract);
        return "redirect:/contracts";
    }
    @GetMapping("/{contractNumber}/edit")
    public String edit(@PathVariable("contractNumber") String contractNumber, Model model) {
        Optional<Contract> contractOpt = contractRepository.findById(contractNumber);
        if (contractOpt.isPresent()) {
            model.addAttribute("contract", contractOpt.get());
            return "contracts/edit";
        } else {
            return "redirect:/contracts";
        }
    }

    @PostMapping("/{contractNumber}/delete")
    public String delete(@PathVariable("contractNumber") String contractNumber) {
        contractRepository.deleteById(contractNumber);
        return "redirect:/contracts";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String contractNumber, Model model) {
        model.addAttribute("contracts", contractRepository.findByContractNumberContainingIgnoreCase(contractNumber));
        return "contracts/index";
    }
}
