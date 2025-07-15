package com.example.__exercice_meubles.controller;

import com.example.__exercice_meubles.entity.Furniture;
import com.example.__exercice_meubles.service.FurnitureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FurnitureController {
    private final FurnitureService furnitureService;

    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping("/")
    public String getAllFurniture(Model model){
        model.addAttribute("furnitures", furnitureService.getAllFurniture());
        return "furniture-list";
    }

    @GetMapping("/addFurniture")
    public String addFurnitureForm(Model model){
        model.addAttribute("furniture", new Furniture());
        return "furniture-form";
    }

    @PostMapping("/addFurniture")
    public String addFurniture(@ModelAttribute("furniture") Furniture furniture){
        furnitureService.save(furniture);
        return "redirect:/";
    }

    @GetMapping("/furniture/delete/{id}")
    public String deleteFurniture(@PathVariable Long id){
        furnitureService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/furniture/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("furniture", furnitureService.getFurnitureById(id));
        return "furniture-form";
    }
}
