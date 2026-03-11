package com.mivan.cats_java_api.service;

import com.mivan.cats_java_api.entity.Cat;
import com.mivan.cats_java_api.exception.CatNotFoundException;
import com.mivan.cats_java_api.exception.DuplicateCatIdException;
import com.mivan.cats_java_api.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private static final String DUPLICATE_MESSAGE = "Ya existe un gato con este ID en la base de datos.";
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public List<Cat> findAll() {
        return catRepository.findAllByOrderByIdDesc();
    }

    public Optional<Cat> findById(Long id) {
        return catRepository.findById(id);
    }

    public Optional<Cat> findByCatId(String catId) {
        return catRepository.findByCatId(catId);
    }

    public Cat save(Cat cat) {
        if (catRepository.existsByCatId(cat.getCatId())) {
            throw new DuplicateCatIdException(DUPLICATE_MESSAGE);
        }
        cat.setApiUsed("java");
        cat.setCreatedAt(Instant.now());
        cat.setUpdatedAt(Instant.now());
        return catRepository.save(cat);
    }

    public Cat update(Long id, Cat cat) {
        Cat existing = catRepository.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Gato no encontrado"));
        if (!existing.getCatId().equals(cat.getCatId()) && catRepository.existsByCatId(cat.getCatId())) {
            throw new DuplicateCatIdException(DUPLICATE_MESSAGE);
        }
        existing.setCatId(cat.getCatId());
        existing.setUrl(cat.getUrl());
        existing.setWidth(cat.getWidth());
        existing.setHeight(cat.getHeight());
        existing.setBreeds(cat.getBreeds());
        existing.setApiUsed("java");
        existing.setUpdatedAt(Instant.now());
        return catRepository.save(existing);
    }

    public void deleteById(Long id) {
        catRepository.deleteById(id);
    }
}
