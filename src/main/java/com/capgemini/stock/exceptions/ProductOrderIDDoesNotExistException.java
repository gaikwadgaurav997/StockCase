package com.capgemini.stock.exceptions;

public class ProductOrderIDDoesNotExistException extends Exception {

	
	private static final long serialVersionUID = 2414710630762797283L;
	
	public ProductOrderIDDoesNotExistException() {
	}

	public ProductOrderIDDoesNotExistException(String message) {
		super(message);
	}

}
