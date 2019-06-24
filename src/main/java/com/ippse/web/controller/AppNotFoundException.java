package com.ippse.web.controller;

public class AppNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5431708871579470673L;

	public AppNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
}
