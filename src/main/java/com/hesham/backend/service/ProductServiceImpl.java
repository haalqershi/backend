package com.hesham.backend.service;

import com.hesham.backend.exception.ProductNotFoundException;
import com.hesham.backend.model.Product;
import com.hesham.backend.repository.CategoryRepository;
import com.hesham.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product, long categoryId){
        System.out.println("product " + product);
        System.out.println(("category id : " + categoryId));
        this.productRepository.addNewProduct(product.getId(), product.getDescription(), product.getImageUrl()
                , product.getName(), product.getPrice(), product.getQuantity(), categoryId);
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
