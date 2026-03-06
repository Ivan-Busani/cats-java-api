package com.mivan.cats_java_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mivan.cats_java_api.dto.SaveCatRequest;
import com.mivan.cats_java_api.entity.Cat;
import com.mivan.cats_java_api.exception.CatNotFoundException;
import com.mivan.cats_java_api.service.CatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cats")
public class CatController {

    private final CatService catService;
    private final ObjectMapper objectMapper;

    public CatController(CatService catService, ObjectMapper objectMapper) {
        this.catService = catService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/health/")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "Java API is running ok"));
    }

    @GetMapping("/list/")
    public ResponseEntity<List<Cat>> catsList() {
        return ResponseEntity.ok(catService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cat> getById(@PathVariable Long id) {
        Cat cat = catService.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Gato no encontrado"));
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/cat_id/{cat_id}")
    public ResponseEntity<Cat> getByCatId(@PathVariable("cat_id") String catId) {
        Cat cat = catService.findByCatId(catId)
                .orElseThrow(() -> new CatNotFoundException("Gato no encontrado"));
        return ResponseEntity.ok(cat);
    }

    @PostMapping("/save/")
    public ResponseEntity<Cat> saveCat(@Valid @RequestBody SaveCatRequest request) throws JsonProcessingException {
        Cat cat = new Cat();
        cat.setCatId(request.getCatId());
        cat.setUrl(request.getUrl());
        cat.setWidth(request.getWidth());
        cat.setHeight(request.getHeight());
        cat.setBreeds(objectMapper.writeValueAsString(request.getBreeds() != null ? request.getBreeds() : List.of()));
        Cat savedCat = catService.save(cat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCat);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cat> updateCat(@PathVariable Long id, @Valid @RequestBody SaveCatRequest request) throws JsonProcessingException {
        Cat cat = new Cat();
        cat.setCatId(request.getCatId());
        cat.setUrl(request.getUrl());
        cat.setWidth(request.getWidth());
        cat.setHeight(request.getHeight());
        cat.setBreeds(objectMapper.writeValueAsString(request.getBreeds() != null ? request.getBreeds() : List.of()));
        Cat updated = catService.update(id, cat);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!catService.findById(id).isPresent()) {
            throw new CatNotFoundException("Gato no encontrado");
        }
        catService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}