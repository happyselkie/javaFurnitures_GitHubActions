package com.example.__exercice_meubles.dao;

import com.example.__exercice_meubles.entity.CartItem;
import com.example.__exercice_meubles.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByFurniture(Furniture furniture);
}
