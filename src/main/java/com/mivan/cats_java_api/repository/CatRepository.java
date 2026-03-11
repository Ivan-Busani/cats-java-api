package com.mivan.cats_java_api.repository;

import com.mivan.cats_java_api.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatRepository extends JpaRepository<Cat, Long> {

    List<Cat> findAllByOrderByIdDesc();

    Optional<Cat> findByCatId(String catId);

    boolean existsByCatId(String catId);
}
