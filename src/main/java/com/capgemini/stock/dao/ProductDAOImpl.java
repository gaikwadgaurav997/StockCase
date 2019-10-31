package com.capgemini.stock.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.stock.dto.ProductStock;
import com.capgemini.stock.entity.ProductStockEntity;
import com.capgemini.stock.util.DBUtil;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String trackProductOrder(ProductStock productStock) {
		
//		Session session = sessionFactory.getCurrentSession();
		Session session = sessionFactory.openSession();
		ProductStockEntity productStockEntity = session.load(ProductStockEntity.class, Integer.parseInt(productStock.getOrderId()));
		// session.getTransaction().commit();
//		if (session.getTransaction() !=null && session.getTransaction().isActive()) {
//			 session.getTransaction().rollback();
//			}
	      
			Date exitDate = productStockEntity.getExitDate();

			Date manDate = productStockEntity.getManufacturingDate();

			String warehouseId = productStockEntity.getWarehouseId();

			if(exitDate == null || manDate == null) {
				return "Data Incomplete...Please check database and update required information";
			}
			
			String message = "The order ID had been in the warehouse with warehouseID = " + warehouseId + " from "
				+ manDate.toString() + " to " + exitDate.toString() + "("
				+ DBUtil.diffBetweenDays(exitDate, manDate) + " days)";
			session.close();
		return message;
	
	
	}

	@Override
	public boolean doesProductOrderIdExist(String orderId) {
		boolean pOrderIdFound = false;
		int oid = -1;
		try {
			oid = Integer.parseInt(orderId);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return pOrderIdFound;
		}
		
//		Session session = sessionFactory.getCurrentSession();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from ProductStockEntity where orderId = :oId");
		query.setParameter("oId", oid);
		if (query.getResultList().size() == 1) {
			pOrderIdFound = true;
			session.getTransaction().commit();
			session.close();
			return pOrderIdFound;
		} else {
//			logger.error(Constants.PRODUCT_ID_DOES_NOT_EXISTS_EXCEPTION);
//			session.close();
			return false;
		}

	}	
	
}
