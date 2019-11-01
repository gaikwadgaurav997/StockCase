package com.capgemini.stock.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.capgemini.stock.dao.ProductDAO;
import com.capgemini.stock.dto.ProductStock;
import com.capgemini.stock.exceptions.ExitDateException;
import com.capgemini.stock.exceptions.ExpiryDateException;
import com.capgemini.stock.exceptions.ManufacturingDateException;
import com.capgemini.stock.exceptions.ProductOrderIDDoesNotExistException;
import com.capgemini.stock.util.JsonUtil;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public String trackProductOrder(ProductStock productStock) {
		String message = productDAO.trackProductOrder(productStock);

		String jsonMessage = JsonUtil.convertJavaToJson(message);
		return jsonMessage;
	}

	@Override
	public boolean doesProductOrderIdExist(String id) throws ProductOrderIDDoesNotExistException {
		return productDAO.doesProductOrderIdExist(id);
	}

	@Override
	public boolean exitDateCheck(ProductStock productStock) throws ExitDateException {
		return productDAO.exitDateCheck(productStock);
	}

	@Override
	public String updateExitDateinStock(ProductStock productStock) {
		String message = productDAO.updateExitDateinStock(productStock);

		String jsonMessage = JsonUtil.convertJavaToJson(message);
		return jsonMessage;
	}
	
	@Override
	public boolean validateManufacturingDate(Date manufacturing_date) throws ManufacturingDateException {
		Date today = new Date();
		if (manufacturing_date.before(today)) {
			return true;
		}
		throw new ManufacturingDateException("You cant enter a future manufacturing date");
	}

	@Override
	public boolean validateExpiryDate(Date manufacturing_date, Date expiry_date) throws ExpiryDateException {
		if (expiry_date.after(manufacturing_date))
			return true;
		throw new ExpiryDateException("You cant enter expiry date before manufacturing date");

	}

	@Override
	public String updateProductStock(ProductStock productStock) {
		String message = productDAO.updateProductStock(productStock);
		String jsonMessage = JsonUtil.convertJavaToJson(message);
		return jsonMessage;
	}

	
}
