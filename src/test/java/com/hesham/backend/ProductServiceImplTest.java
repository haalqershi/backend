package com.hesham.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hesham.backend.model.Product;
import com.hesham.backend.service.ProductService;

@SpringBootTest
public class ProductServiceImplTest {
	@Autowired
	ProductService service;
//	@Test
//	public void testAddProject() {
//		Product p = new Product(13l, "a", 12, 12, "nv", "rg");
//		service.addProduct(p,13l);
//		
//		Product e1 = service.findProductById(13l);
//		assertEquals("a", e1.getName());
//	}
//	@Test
//	public void testUpdateProject() {
//		Product e = new Product(30l, "abc1", 12, 12, "nv", "rg");
//		service.addProduct(e, 30l);
//		e.setName("abc2");
//		service.updateProduct(e);
//		Product e1 = service.findProductById(30l);
//		assertEquals("abc2", e1.getName());
//	}
	@Test
	public void testDeleteProject() {
		Product d = new Product(8l, "d", 12, 12, "nv", "rg");
		service.addProduct(d, 8l);
		Product d1 = service.findProductById(8l);
		assertEquals("d", d1.getName());
		service.deleteProduct(8l);
		d1 = service.findProductById(8l);
		assertEquals("User by id 8 was not found", d1);
	}

}
