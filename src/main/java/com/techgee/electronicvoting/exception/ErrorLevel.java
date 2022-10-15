package com.techgee.electronicvoting.exception;

public enum ErrorLevel {
	REQUEST,
	BUSINESS,
	SECURITY,
	CONFLICT,
	RESOURCE;
	
	public static ErrorLevel getErrorLevel(String level){
		if(level.equals("REQUEST")) {
			return REQUEST;
		}
		if(level.equals("BUSINESS")) {
			return BUSINESS;
		}
		if(level.equals("SECURITY")) {
			return SECURITY;
		}
		if(level.equals("CONFLICT")) {
			return CONFLICT;
		}
		if(level.equals("RESOURCE")) {
			return RESOURCE;
		}
		return REQUEST;		
	}
}
