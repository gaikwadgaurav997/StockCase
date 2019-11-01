package com.capgemini.stock.entity;

	import java.util.Date;
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.Table;

	import java.util.Date;


	@Entity
	@Table(name = "RawmaterialOrders")
	public class RawMaterialOrderEntity {
		 public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		@Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    @Column(name = "orderid")
		private int orderId;
		 @Column(name = "name")
		private String name;
		// private static int orderIdCount = 1;
		 @Column(name = "supplierid")
		private String supplierId;
		 @Column(name = "quantityvalue")
		private double quantityValue;
		 @Column(name = "quantityunit")
		private String quantityUnit;
		 @Column(name = "dateoforder")
		private Date dateOfOrder;
		 @Column(name = "dateofdelivery")
		private Date dateOfDelivery;
		 @Column(name = "priceperunit")
		private double pricePerUnit;
		 @Column(name = "totalprice")
		private double totalPrice;
		 @Column(name = "deliverystatus")
		private String deliveryStatus; 
		 @Column(name = "warehouseid")
		private String warehouseId;

		public RawMaterialOrderEntity(String name, String supplierId, double quantityValue, String quantityUnit,
				Date dateOfDelivery, double pricePerUnit, String warehouseId) {
			super();
			this.name = name;
			this.supplierId = supplierId;
			this.quantityValue = quantityValue;
			this.quantityUnit = quantityUnit;
			this.dateOfOrder = new Date();
			this.dateOfDelivery = dateOfDelivery;
			this.pricePerUnit = pricePerUnit;
			this.totalPrice = this.quantityValue * this.pricePerUnit;
			this.deliveryStatus = "PENDING";
			this.warehouseId = warehouseId;
		}

		@Override
		public String toString() {
			return "RawMaterialOrder [orderId=" + orderId + ", name=" + name + ", supplierId="
					+ supplierId + ", quantityValue=" + quantityValue + ", quantityUnit=" + quantityUnit + ", dateOfOrder="
					+ dateOfOrder + ", dateOfDelivery=" + dateOfDelivery + ", pricePerUnit=" + pricePerUnit
					+ ", totalPrice=" + totalPrice + ", deliveryStatus=" + deliveryStatus + ", warehouseId=" + warehouseId
					+ "]";
		}

//		public RawMaterialOrder(String orderId, String name, String rmId, String supplierId, double quantityValue,
//				String quantityUnit, Date dateOfOrder, Date dateOfDelivery, double pricePerUnit, double totalPrice,
//				String deliveryStatus, String warehouseId) {
//			super();
//			this.orderId = orderId;
//			this.name = name;
//			this.rmId = rmId;
//			this.supplierId = supplierId;
//			this.quantityValue = quantityValue;
//			this.quantityUnit = quantityUnit;
//			this.dateOfOrder = dateOfOrder;
//			this.dateOfDelivery = dateOfDelivery;
//			this.pricePerUnit = pricePerUnit;
//			this.totalPrice = totalPrice;
//			this.deliveryStatus = deliveryStatus;
//			this.warehouseId = warehouseId;
//		}

		public RawMaterialOrderEntity() {
		}

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

//		public static int getOrderIdCount() {
//			return orderIdCount;
//		}

//		public static void setOrderIdCount(int orderIdCount) {
//			RawMaterialOrderEntity.orderIdCount = orderIdCount;
//		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSupplierId() {
			return supplierId;
		}

		public void setSupplierId(String supplierId) {
			this.supplierId = supplierId;
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

		public Date getDateOfOrder() {
			return dateOfOrder;
		}

		public void setDateOfOrder(Date dateOfOrder) {
			this.dateOfOrder = dateOfOrder;
		}

		public Date getDateOfDelivery() {
			return dateOfDelivery;
		}

		public void setDateOfDelivery(Date dateOfDelivery) {
			this.dateOfDelivery = dateOfDelivery;
		}

		public double getPricePerUnit() {
			return pricePerUnit;
		}

		public void setPricePerUnit(double pricePerUnit) {
			this.pricePerUnit = pricePerUnit;
		}

		public String getDeliveryStatus() {
			return deliveryStatus;
		}

		public void setDeliveryStatus(String deliveryStatus) {
			this.deliveryStatus = deliveryStatus;
		}

		public String getWarehouseId() {
			return warehouseId;
		}

		public void setWarehouseId(String warehouseId) {
			this.warehouseId = warehouseId;
		}
	}
