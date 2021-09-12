package com.hesham.backend.service;

import com.hesham.backend.exception.ProductNotFoundException;
import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.repository.CategoryRepository;
import com.hesham.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product addProduct(Product product, long categoryId){
        Product newProduct = this.productRepository.save(product);
        Category category = this.categoryRepository.findById(categoryId).orElse(null);
        category.getProducts().add(newProduct);
        return newProduct;
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

    public List<Product> findProductsByCategoryId(@RequestParam("id") long id){
        return this.productRepository.findProductsByCategoryId(id);
    }

}
