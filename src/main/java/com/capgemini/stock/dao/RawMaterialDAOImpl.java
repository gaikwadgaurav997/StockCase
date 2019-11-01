package com.capgemini.stock.dao;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.capgemini.stock.exceptions.ProcessDateException;
import com.capgemini.stock.exceptions.RMOrderIDDoesNotExistException;
import com.capgemini.stock.dao.Constants;
import com.capgemini.stock.dto.RawMaterialStock;
import com.capgemini.stock.entity.RawMaterialStockEntity;
import com.capgemini.stock.util.DBUtil;

@Repository
public class RawMaterialDAOImpl implements RawMaterialDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String trackRawMaterialOrder(RawMaterialStock rawMaterialStock) {
		

        Session session = sessionFactory.openSession();
        session.beginTransaction();
		
		System.out.println(rawMaterialStock.getOrderId());
		
        RawMaterialStockEntity rmStockEntity = session.load(RawMaterialStockEntity.class, Integer.parseInt(rawMaterialStock.getOrderId()));
//	     System.out.println(rmStockEntity);
      
//	      session.getTransaction().commit();
	      
	      				Date processDate = rmStockEntity.getProcessDate();
	      
	      				Date deliveryDate = rmStockEntity.getDateofDelivery();
	      
	      				String warehouseId = rmStockEntity.getWarehouseId();
	      
	      			if(processDate == null || deliveryDate == null) {
	      				return "Data Incomplete...Please check database and update required information";
	      			}
	      
	      			String message = "The order ID had been in the warehouse with warehouseID = " + warehouseId + " from "
	      					+ deliveryDate.toString() + " to " + processDate.toString() + "("
	      					+ DBUtil.diffBetweenDays(processDate, deliveryDate) + " days)";
	      			session.close();
	      			return message;
	    
	}

	@Override
	public boolean doesRawMaterialOrderIdExist(String orderId) throws RMOrderIDDoesNotExistException {
		boolean rmOrderIdFound = false;
		int oid = -1;
		try {
			oid = Integer.parseInt(orderId);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return rmOrderIdFound;
		}
		
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from RawMaterialOrderEntity where orderId = :oId");
		query.setParameter("oId", oid);
		if (query.getResultList().size() == 1) {
			rmOrderIdFound = true;
//			session.getTransaction().commit();
			session.close();
			return rmOrderIdFound;
		} else {
//			logger.error(Constants.RAWMATERIAL_ID_DOES_NOT_EXISTS_EXCEPTION);
			session.close();
			throw new RMOrderIDDoesNotExistException(Constants.RAWMATERIAL_ID_DOES_NOT_EXISTS_EXCEPTION);
		}

	}

	@Override
	public boolean processDateCheck(RawMaterialStock rawMaterialStock) throws ProcessDateException {
		
		Session session = null;
		
		try {
			boolean datecheck = false;
			session = sessionFactory.openSession(); 
	        session.beginTransaction();

	        RawMaterialStockEntity rmStockEntity = session.load(RawMaterialStockEntity.class, Integer.parseInt(rawMaterialStock.getOrderId()));
		    
//	        session.getTransaction().commit();
	        
		      Date manufacturingDate = rmStockEntity.getManufacturingDate();
		      
		      Date expiryDate = rmStockEntity.getExpiryDate();
		      
		      				if (rawMaterialStock.getProcessDate().after(manufacturingDate)
		      						&& rawMaterialStock.getProcessDate().before(expiryDate)) {
		      					datecheck = true;
		      					return datecheck;
		      				}
		      
		      				else
		      					throw new ProcessDateException(Constants.PROCESS_DATE_EXCEPTION_MESSAGE);
		      
		      			
		      
		      		} 
		      
		      		catch (ProcessDateException exception) {
//		      			logger.error(Constants.PROCESS_DATE_EXCEPTION_MESSAGE);
		      			throw exception;
		      
		      		}
		finally {
			session.close();
		}
		  
			

		
	}

	@Override
	public String updateProcessDateinStock(RawMaterialStock rawMaterialStock) {

		Session session = sessionFactory.openSession(); 
        session.beginTransaction();
        String hql = "update RawMaterialStockEntity set processDate = :processDateVariable where orderId = :oId";
        Query q = session.createQuery(hql);
	      q.setParameter("oId", Integer.parseInt(rawMaterialStock.getOrderId()));
	      q.setParameter("processDateVariable", rawMaterialStock.getProcessDate());
	      int result = q.executeUpdate();
	      System.out.println(result);
	      session.getTransaction().commit();
	      if (session.getTransaction() != null && session.getTransaction().isActive()) {
				 session.getTransaction().rollback();
			}
	      session.close();
	      return Constants.DATA_INSERTED_MESSAGE;
		

	}

	@Override
	public String updateRawMaterialStock(RawMaterialStock rawMaterialStock) {
		
		Session session = sessionFactory.openSession(); 
        session.beginTransaction();
        
        boolean orderIdcheckInStock = false;
        System.out.println("1");
		orderIdcheckInStock = doesRawMaterialOrderIdExistInStock(rawMaterialStock.getOrderId());
		System.out.println("2");
		if (orderIdcheckInStock == false) {
			System.out.println("3");
			String hql = "insert into RawMaterialStockEntity(orderId, name, pricePerUnit, quantityValue, quantityUnit, totalPrice, warehouseId, dateofDelivery)" +  " select orderId, name, pricePerUnit, quantityValue, quantityUnit, totalPrice, warehouseId, dateOfDelivery from RawMaterialOrderEntity where orderId = :oId";
			Query q = session.createQuery(hql);
		      q.setParameter("oId", Integer.parseInt(rawMaterialStock.getOrderId()));
			
			int result = q.executeUpdate();
			System.out.println(result + ":");
		}
		System.out.println("4");
		String hql = "update RawMaterialStockEntity set manufacturingDate = :manDate, expiryDate = :expDate, qualityCheck = :qaCheck where orderID = :oId";
		Query q1 = session.createQuery(hql);
	      q1.setParameter("oId", Integer.parseInt(rawMaterialStock.getOrderId()));
	      q1.setParameter("manDate", rawMaterialStock.getManufacturingDate());
	      q1.setParameter("expDate", rawMaterialStock.getExpiryDate());
	      q1.setParameter("qaCheck", rawMaterialStock.getQualityCheck());
	      
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

	
	public boolean doesRawMaterialOrderIdExistInStock(String orderId) {
		
		boolean rmOrderIdFound = false;
		int oid = -1;
		try {
			oid = Integer.parseInt(orderId);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			return rmOrderIdFound;
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from RawMaterialStockEntity where orderId = :oId");
		query.setParameter("oId", oid);
		if (query.getResultList().size() == 1) {
			rmOrderIdFound = true;
//			session.getTransaction().commit();
			session.close();
			return rmOrderIdFound;
		} else {
//			logger.error(Constants.RAWMATERIAL_ID_DOES_NOT_EXIST_IN_STOCK_EXCEPTION);
			session.close();
			return rmOrderIdFound;
		}	 

	}
	
	
	
	
}
