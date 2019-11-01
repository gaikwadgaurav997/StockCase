package com.capgemini.stock.service;

import java.util.Date;
import com.capgemini.stock.exceptions.ExpiryDateException;
import com.capgemini.stock.exceptions.ManufacturingDateException;
import com.capgemini.stock.exceptions.ProcessDateException;
import com.capgemini.stock.dto.RawMaterialStock;
import com.capgemini.stock.exceptions.RMOrderIDDoesNotExistException;

public interface RawMaterialService {

	public String trackRawMaterialOrder(RawMaterialStock rawMaterialStock);

	public boolean doesRawMaterialOrderIdExist(String id) throws RMOrderIDDoesNotExistException;

	public boolean processDateCheck(RawMaterialStock rawMaterialStock) throws ProcessDateException;

	public String updateProcessDateinStock(RawMaterialStock rawMaterialStock);

	public boolean validateManufacturingDate(Date manufacturing_date) throws ManufacturingDateException;

	public boolean validateExpiryDate(Date manufacturing_date, Date expiry_date) throws ExpiryDateException;

	public String updateRawMaterialStock(RawMaterialStock rawMaterialStock);

	

}
