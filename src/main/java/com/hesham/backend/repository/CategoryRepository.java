package com.hesham.backend.repository;

import com.hesham.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(
            value =
                    "insert into category (id, categoryName) values (:id, :category_name)",
            nativeQuery = true)
    void addNewCategory(@Param("id") Long id
            , @Param("category_name") String categoryName);

}
