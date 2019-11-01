package com.capgemini.stock.dao;


import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.capgemini.stock.exceptions.ExitDateException;
import com.capgemini.stock.exceptions.ProductOrderIDDoesNotExistException;
import com.capgemini.stock.dao.Constants;
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
	public boolean doesProductOrderIdExist(String orderId) throws ProductOrderIDDoesNotExistException {
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
			throw new ProductOrderIDDoesNotExistException(Constants.PRODUCT_ID_DOES_NOT_EXISTS_EXCEPTION);
		}

	}

	@Override
	public boolean exitDateCheck(ProductStock productStock) throws ExitDateException {
		
		Session session = null;
		try {
			boolean datecheck = false;
			session = sessionFactory.openSession();
			session.beginTransaction();
//	        String hql = "select manufacturingDate, expiryDate from ProductStockEntity where orderId = :oId";
//	        Query q = session.createQuery(hql);
//		      q.setParameter("oId", Integer.parseInt(productStock.getOrderId()));
//		      Object[] dateDetails = (Object[]) q.uniqueResult();
		      
	        ProductStockEntity productStockEntity = session.load(ProductStockEntity.class, Integer.parseInt(productStock.getOrderId()));
//		      session.getTransaction().commit();
		      
		      Date manufacturingDate = productStockEntity.getManufacturingDate();
		      
				Date expiryDate = productStockEntity.getExpiryDate();
				
				if (productStock.getExitDate().after(manufacturingDate)	&& productStock.getExitDate().before(expiryDate)) {
					datecheck = true;
					return datecheck;
				}
				
				throw new ExitDateException(Constants.EXIT_DATE_EXCEPTION);
			}	
				
			catch (ExitDateException exception) {
//					logger.error(exception.getMessage());
					throw exception;
		
				}
		
		finally {
			session.close();
		}
			

	}

	@Override
	public String updateExitDateinStock(ProductStock productStock) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
        String hql = "update ProductStockEntity set exitDate = :exitDateVariable where orderId = :oId";
        Query q = session.createQuery(hql);
	      q.setParameter("oId", Integer.parseInt(productStock.getOrderId()));
	      q.setParameter("exitDateVariable", productStock.getExitDate());
	      int result = q.executeUpdate();
	      session.getTransaction().commit();
	  	
	    if (session.getTransaction() != null && session.getTransaction().isActive()) {
			 session.getTransaction().rollback();
		}
	      
	    session.close();
	    
	    return Constants.DATA_INSERTED_MESSAGE;
	}

	@Override
	public String updateProductStock(ProductStock productStock) {
		
		Session session = sessionFactory.openSession(); 
        session.beginTransaction();
        
        boolean orderIdcheckInStock = false;
        System.out.println("1");
		orderIdcheckInStock = doesProductOrderIdExistInStock(productStock.getOrderId());
		System.out.println("2");
		if (orderIdcheckInStock == false) {
			System.out.println("3");
			String hql = "insert into ProductStockEntity(orderId, name, pricePerUnit, quantityValue, quantityUnit, totalPrice, warehouseId, dateofDelivery)" +  " select orderId, name, pricePerUnit, quantityValue, quantityUnit, totalPrice, warehouseId, dateofDelivery from ProductOrdersEntity where orderId = :oId";
			Query q = session.createQuery(hql);
		      q.setParameter("oId", Integer.parseInt(productStock.getOrderId()));
			
			int result = q.executeUpdate();
			System.out.println(result + ":");
		}
		System.out.println("4");
		String hql = "update ProductStockEntity set manufacturingDate = :manDate, expiryDate = :expDate, qualityCheck = :qaCheck where orderID = :oId";
		Query q1 = session.createQuery(hql);
	      q1.setParameter("oId", Integer.parseInt(productStock.getOrderId()));
	      q1.setParameter("manDate", productStock.getManufacturingDate());
	      q1.setParameter("expDate", productStock.getExpiryDate());
	      q1.setParameter("qaCheck", productStock.getQualityCheck());
	      
	      int result = q1.executeUpdate();
			System.out.println(result);
			System.out.println("5");
			session.getTransaction().commit();
			if (session.getTransaction() != null && session.getTransaction().isActive()) {
				 session.getTransaction().rollback();
			}
		    session.close();
		      return Constants.DATA_INSERTED_MESSAGE;
       

	}

	private boolean doesProductOrderIdExistInStock(String orderId) {
		
		boolean productOrderIdFound = false;
		int oid = -1;
		try {
			oid = Integer.parseInt(orderId);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return productOrderIdFound;
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from ProductStockEntity where orderId = :oId");
		query.setParameter("oId", oid);
		if (query.getResultList().size() == 1) {
			productOrderIdFound = true;
//			session.getTransaction().commit();
			session.close();
			return productOrderIdFound;
		} else {
//			logger.error(Constants.PRODUCT_ID_DOES_NOT_EXIST_IN_STOCK_EXCEPTION);
			session.close();
			return productOrderIdFound;
		}
	}
	
	
	
	
}
