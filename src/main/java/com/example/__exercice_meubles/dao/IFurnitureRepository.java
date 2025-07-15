package com.example.__exercice_meubles.dao;

import com.example.__exercice_meubles.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFurnitureRepository extends JpaRepository<Furniture, Long> {
}
