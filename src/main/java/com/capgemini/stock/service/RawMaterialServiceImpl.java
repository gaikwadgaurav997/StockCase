package com.capgemini.stock.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.capgemini.stock.dao.RawMaterialDAO;
import com.capgemini.stock.dto.RawMaterialStock;
import com.capgemini.stock.exceptions.ExpiryDateException;
import com.capgemini.stock.exceptions.ManufacturingDateException;
import com.capgemini.stock.exceptions.ProcessDateException;
import com.capgemini.stock.exceptions.RMOrderIDDoesNotExistException;
import com.capgemini.stock.util.JsonUtil;

@Service
@Transactional(readOnly = true)
public class RawMaterialServiceImpl implements RawMaterialService {

	@Autowired
	private RawMaterialDAO rawMaterialDAO;
	
	@Override
	public String trackRawMaterialOrder(RawMaterialStock rawMaterialStock) {
		String message = rawMaterialDAO.trackRawMaterialOrder(rawMaterialStock);

		String jsonMessage = JsonUtil.convertJavaToJson(message);
		return jsonMessage;
	}

	@Override
	public boolean doesRawMaterialOrderIdExist(String id) throws RMOrderIDDoesNotExistException {
		return rawMaterialDAO.doesRawMaterialOrderIdExist(id);
	}

	@Override
	public boolean processDateCheck(RawMaterialStock rawMaterialStock) throws ProcessDateException {
		return rawMaterialDAO.processDateCheck(rawMaterialStock);
	}

	@Override
	public String updateProcessDateinStock(RawMaterialStock rawMaterialStock) {
		String message = rawMaterialDAO.updateProcessDateinStock(rawMaterialStock);

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
	public String updateRawMaterialStock(RawMaterialStock rawMaterialStock) {
		String message = rawMaterialDAO.updateRawMaterialStock(rawMaterialStock);
		String jsonMessage = JsonUtil.convertJavaToJson(message);
		return jsonMessage;
	}

	
}
