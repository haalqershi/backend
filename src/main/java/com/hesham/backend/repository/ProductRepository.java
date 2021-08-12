package com.hesham.backend.repository;

import com.hesham.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    void deleteProductById(Long id);

    Optional<Product> findProductById(Long aLong);

    @Query(value = "SELECT * FROM product p WHERE p.category_id = :category_id",
            nativeQuery = true)
    List<Product> findProductsByCategoryId(
            @Param("category_id") Long category_id);


    @Modifying
    @Transactional
    @Query(
            value =
                    "insert into product (id, description, image_url, name, price, quantity, category_id) values (:id, :description, :image_url, :name, :price, :quantity, :category_id)",
            nativeQuery = true)
    void addNewProduct(@Param("id") Long id
            , @Param("description") String description
            , @Param("image_url") String image_url
            , @Param("name") String name
            , @Param("price")  Double price
            , @Param("quantity") Integer quantity
            , @Param("category_id") Long category_id);
}
