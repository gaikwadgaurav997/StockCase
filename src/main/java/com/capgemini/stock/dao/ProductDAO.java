package com.capgemini.stock.dao;

import com.capgemini.stock.dto.ProductStock;

public interface ProductDAO {
	
	public boolean doesProductOrderIdExist(String orderId);

	public String trackProductOrder(ProductStock productStock);
}
