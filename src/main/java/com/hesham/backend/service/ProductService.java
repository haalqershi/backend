package com.hesham.backend.service;

import com.hesham.backend.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product, long categoryId);

    List<Product> findAllProducts();

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Product findProductById(Long id);

    List<Product> findProductsByCategoryId(long id);
}
