package com.hesham.backend.controller;

import com.hesham.backend.model.Product;
import com.hesham.backend.service.CategoryService;
import com.hesham.backend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/product")
@Api(value = "Product Resource REST Endpoint", description = "Shows product information")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Find List of Products", notes = "used to retrieve list of products", response = Product.class)
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = this.productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find a Product", notes = "used to retrieve a product by id", response = Product.class)
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product product = this.productService.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    @ApiOperation(value = "Add a New Product", notes = "used to create a new product", response = Product.class)
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @PathVariable("id") long id){
        this.productService.addProduct(product, id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update a Product", notes = "used to update a product", response = Product.class)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product updatedProduct = this.productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a Product", notes = "used to delete a product by id", response = Product.class)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        this.productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    @ApiOperation(value = "Find Products by Category Id", notes = "used to retrieve products by category id", response = Product.class)
    public List<Product> findProductsByCategoryId(@PathVariable("id") long id){
        return this.productService.findProductsByCategoryId(id);
    }

}
