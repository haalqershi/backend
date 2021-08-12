package com.hesham.backend.service;

import com.hesham.backend.exception.ProductNotFoundException;
import com.hesham.backend.model.Product;
import com.hesham.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product){
        return this.productRepository.save(product);
    }

    public List<Product> findAllProducts(){
        return this.productRepository.findAll();
    }

    public Product updateProduct(Product product){
        return this.productRepository.save(product);
    }

    public void deleteProduct(Long id){
        this.productRepository.deleteById(id);
    }

    public Product findProductById(Long id){
        return this.productRepository
                .findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("User by id " + id + " was not found"));
    }

}
