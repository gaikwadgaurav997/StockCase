package com.capgemini.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.stock.dao.ProductDAO;
import com.capgemini.stock.dto.ProductStock;
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

	
}
