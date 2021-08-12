package com.hesham.backend.repository;

import com.hesham.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(
            value =
                    "insert into category (id, category_name) values (:id, :category_name)",
            nativeQuery = true)
    void addNewCategory(@Param("id") Long id
            , @Param("category_name") String category_name);

}
