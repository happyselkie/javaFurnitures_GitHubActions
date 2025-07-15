package com.example.__exercice_meubles.service;

import com.example.__exercice_meubles.dao.ICartItemRepository;
import com.example.__exercice_meubles.entity.CartItem;
import com.example.__exercice_meubles.entity.Furniture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final ICartItemRepository cartItemRepository;
    private final FurnitureService furnitureService;

    public CartItemService(ICartItemRepository cartItemRepository, FurnitureService furnitureService) {
        this.cartItemRepository = cartItemRepository;
        this.furnitureService = furnitureService;
    }

    public List<CartItem> getAllCart() {
        return cartItemRepository.findAll();
    }

    public Boolean addToCart(CartItem cartItem){
        // Vérification de l'existance de la furniture dans la BDD
        Furniture furnitureExist = furnitureService.getFurnitureById(cartItem.getFurniture().getId());

        if(furnitureExist != null) {
            // On vérifie si la quantité demandée est inférieur au stock disponible
            if (furnitureExist.getStock() < cartItem.getQuantity()){
                return false;
            }
            // On vérifie si la fourniture existe déjà dans le panier
            CartItem existingCartItem = cartItemRepository.findByFurniture(cartItem.getFurniture());
            // Si l'objet est déjà dans le panier, on ajoute la quantité demandée à la quantité initiale
            if (existingCartItem != null){
                int newQuantity = existingCartItem.getQuantity() + cartItem.getQuantity();
                // Sauvegarde de la nouvelle quantitée
                existingCartItem.setQuantity(newQuantity);
                cartItemRepository.save(existingCartItem);
            // Si l'objet n'est pas dans le panier, alors on ajoute l'élèment complet dans le panier
            } else {
                cartItemRepository.save(cartItem);
            }
            // Mise à jour du stock disponible pour l'objet
            furnitureExist.setStock(furnitureExist.getStock() - cartItem.getQuantity());
            furnitureService.save(furnitureExist);
        }

        return true;
    }

    public void removeFromCart(Long id){
        // On vérifie si l'objet à supprimer est bien dans le panier
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        if (cartItem != null){
            // On stock dans une variable l'objet à supprimer du panier afin de mettre à jour le stock
            Furniture furnitureExist = furnitureService.getFurnitureById(cartItem.getFurniture().getId());
            if (furnitureExist.getId() != null){
                // Ajout de la quantité du panier à notre stock
                furnitureExist.setStock(furnitureExist.getStock() + cartItem.getQuantity());
//                furnitureService.save(furnitureExist);
                // Suppression de l'objet du panier
                cartItemRepository.delete(cartItem);
            }
        }
    }

    public void clearCart(){
        List<CartItem> cartItemList = cartItemRepository.findAll();
        for (CartItem cartItem : cartItemList){
            removeFromCart(cartItem.getId());
        }
    }
}
