package com.example.__exercice_meubles.service;

import com.example.__exercice_meubles.dao.IFurnitureRepository;
import com.example.__exercice_meubles.entity.Furniture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FurnitureService {
    private final IFurnitureRepository furnitureRepository;

    public FurnitureService(IFurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    public List<Furniture> getAllFurniture() {
        return furnitureRepository.findAll();
    }

    public Furniture getFurnitureById(Long id) {
        return furnitureRepository.findById(id).orElse(null);
    }

    public Furniture save(Furniture furniture) {
        return furnitureRepository.save(furniture);
    }

    public void delete(Long id) {
        furnitureRepository.deleteById(id);
    }
}
