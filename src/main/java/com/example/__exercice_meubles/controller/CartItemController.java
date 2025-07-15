package com.example.__exercice_meubles.controller;

import com.example.__exercice_meubles.entity.CartItem;
import com.example.__exercice_meubles.service.CartItemService;
import com.example.__exercice_meubles.service.FurnitureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final FurnitureService furnitureService;

    public CartItemController(CartItemService cartItemService, FurnitureService furnitureService) {
        this.cartItemService = cartItemService;
        this.furnitureService = furnitureService;
    }

    @GetMapping()
    public String getCart(Model model) {
        model.addAttribute("cartItems", cartItemService.getAllCart());
        return "cart";
    }

    @PostMapping("/add/{furnitureId}")
    public String addToCart(@PathVariable Long furnitureId, @RequestParam int quantity){
        CartItem cartItem = new CartItem();
        cartItem.setFurniture(furnitureService.getFurnitureById(furnitureId));
        cartItem.setQuantity(quantity);
        if (!cartItemService.addToCart(cartItem)){
            return "redirect:/";
        }

        return "redirect:/cartItem";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        cartItemService.removeFromCart(id);
        return "redirect:/cartItem";
    }

    @GetMapping("/clear")
    public String deleteAll(){
        cartItemService.clearCart();
        return "redirect:/cartItem";
    }
}
