package com.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.Product;
import com.product.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Slf4j
@Service
@Qualifier("ProductService")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	//Logger logger=LoggerFactory.getLogger(ElkStackExampleApplication.class);

	public List<Product> getProductList()  {
		
		List<Product> products = productRepository.findAll();
		
		if(products.size() == 0) {
			Product product1 = new Product("1", "Apple Iphone 13", "60000", "128GB storage, 8GB Ram", "10", false);
			Product product2 = new Product("2", "Samsung Galaxy S21", "50000", "256GB storage, 8GB Ram", "10", false);
			Product product3 = new Product("3", "VIVO V9 Pro", "45000", "128GB storage, 6GB Ram", "12", false);
			
			products.add(product1);
			products.add(product2);
			products.add(product3);
		}
		
		try {
			log.info("Sending product {}",new ObjectMapper().writeValueAsString(products));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return products;

		
	}
	
	public boolean createProducts(List<Product> productList) {
		try {
			productRepository.saveAll(productList);
			log.info("Adding product {}",new ObjectMapper().writeValueAsString(productList));
			//log.info("Adding product {}",productList);
			
		}
		catch (IllegalArgumentException e) {
			// In case the given entities or any of the entities is null.
			log.error("Product could not be added : {}",e);
			return false;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean deleteProducts(List<Product> productList) {
		try {
			productRepository.deleteAll(productList);
			//log.info("Deleting product {}",productList);
			log.info("Deleting product {}",new ObjectMapper().writeValueAsString(productList));
		}
		catch (Exception e) {
			// In case the given entities or any of the entities is null.
			// or the given id is not found
			log.error("Product could not be deleted : {}",e);
			return false;
		}
		return true;
	}
	
	public long getProductsCount() {
		log.info("Product count {}",productRepository.count());
		return productRepository.count();
	}
}
