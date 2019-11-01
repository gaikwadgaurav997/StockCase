package com.capgemini.stock.dao;

import com.capgemini.stock.dto.RawMaterialStock;
import com.capgemini.stock.exceptions.ProcessDateException;
import com.capgemini.stock.exceptions.RMOrderIDDoesNotExistException;

public interface RawMaterialDAO {
	
	public boolean doesRawMaterialOrderIdExist(String orderId) throws RMOrderIDDoesNotExistException;

	public String trackRawMaterialOrder(RawMaterialStock rawMaterialStock);

	public boolean processDateCheck(RawMaterialStock rawMaterialStock) throws ProcessDateException;

	public String updateProcessDateinStock(RawMaterialStock rawMaterialStock);

	public String updateRawMaterialStock(RawMaterialStock rawMaterialStock);

	
}
