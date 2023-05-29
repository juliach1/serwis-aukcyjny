package com.aukcje.repository;

import com.aukcje.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> getById(Integer id);
    List<Category> findByIdNot(Integer id);

    List<Category> getCategoriesByParentCategory(Category category);

    List<Category> getCategoriesByParentCategoryIsNull();

    @Query("SELECT c.parentCategory FROM Category c WHERE c.id = ?1")
    Optional<Category> getParentCategoryBySubCategoryId(Integer id);


}
