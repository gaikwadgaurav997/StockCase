package com.capgemini.stock.dao;

import com.capgemini.stock.dto.ProductStock;
import com.capgemini.stock.exceptions.ExitDateException;
import com.capgemini.stock.exceptions.ProductOrderIDDoesNotExistException;

public interface ProductDAO {
	
	public boolean doesProductOrderIdExist(String orderId) throws ProductOrderIDDoesNotExistException;

	public String trackProductOrder(ProductStock productStock);

	public boolean exitDateCheck(ProductStock productStock) throws ExitDateException;

	public String updateExitDateinStock(ProductStock productStock);

	public String updateProductStock(ProductStock productStock);
}
