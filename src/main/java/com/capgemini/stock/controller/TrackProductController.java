package com.capgemini.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.stock.dto.ProductStock;
import com.capgemini.stock.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
public class TrackProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/stock/TrackProduct/{id}")
	public String trackProductOrder(@PathVariable("id") String id) {
		
		String message = productService.trackProductOrder(new ProductStock(id));
//		return ResponseEntity.ok().body(message);
		return message;
	   
	}
	

}
