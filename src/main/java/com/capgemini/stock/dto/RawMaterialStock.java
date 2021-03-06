package com.capgemini.stock.dto;

import java.util.Date;

public class RawMaterialStock {
	// psId, distributorId, warehouseId can be accessed from order table
	
	private String orderId;
	private String name;
	private double price_per_unit;
	private double quantityValue;
	private String quantityUnit;
	private double price;
	private String warehouseID;
	private Date deliveryDate;
	private Date manufacturingDate;
	private Date expiryDate;
	private String qualityCheck;
	private Date processDate;
	
	
	public RawMaterialStock() {
		super();
	}
	
	public RawMaterialStock(String orderId, Date manufacturingDate, Date expiryDate, String qualityCheck) {
		super();
		setOrderId(orderId);
		setManufacturingDate(manufacturingDate);
		setExpiryDate(expiryDate);
		setQualityCheck(qualityCheck);
	}


	public RawMaterialStock(String orderId, Date processDate) {
		super();
		setOrderId(orderId);
		setProcessDate(processDate);
	}
	
	public RawMaterialStock(String orderId) {
		super();
		setOrderId(orderId);
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice_per_unit() {
		return price_per_unit;
	}


	public void setPrice_per_unit(double price_per_unit) {
		this.price_per_unit = price_per_unit;
	}


	public double getQuantityValue() {
		return quantityValue;
	}


	public void setQuantityValue(double quantityValue) {
		this.quantityValue = quantityValue;
	}


	public String getQuantityUnit() {
		return quantityUnit;
	}


	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getWarehouseID() {
		return warehouseID;
	}


	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public Date getManufacturingDate() {
		return manufacturingDate;
	}


	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getQualityCheck() {
		return qualityCheck;
	}


	public void setQualityCheck(String qualityCheck) {
		this.qualityCheck = qualityCheck;
	}


	public Date getProcessDate() {
		return processDate;
	}


	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	


	
	
	
	
}

	