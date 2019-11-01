package com.capgemini.stock.service;

import com.capgemini.stock.exceptions.ExitDateException;

import java.util.Date;

import com.capgemini.stock.exceptions.ExpiryDateException;
import com.capgemini.stock.exceptions.ManufacturingDateException;
import com.capgemini.stock.dto.ProductStock;
import com.capgemini.stock.exceptions.ProductOrderIDDoesNotExistException;

public interface ProductService {

	public String trackProductOrder(ProductStock productStock);

	public boolean doesProductOrderIdExist(String id) throws ProductOrderIDDoesNotExistException;

	public boolean exitDateCheck(ProductStock productStock) throws ExitDateException;

	public String updateExitDateinStock(ProductStock productStock);

	public boolean validateManufacturingDate(Date manufacturing_date) throws ManufacturingDateException;

	public boolean validateExpiryDate(Date manufacturing_date, Date expiry_date) throws ExpiryDateException;

	public String updateProductStock(ProductStock productStock);

}
