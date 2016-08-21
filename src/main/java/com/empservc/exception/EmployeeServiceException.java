package com.empservc.exception;

public class EmployeeServiceException extends Exception {

	private static final long serialVersionUID = -6033042812038495826L;
	public EmployeeServiceException() {
		  super();
	  }
	  
	  public EmployeeServiceException(String message) {
		  super(message);
	  }
	  public EmployeeServiceException(String message, Throwable cause) {
		  super(message, cause);
	  }
	  
	  public EmployeeServiceException(Throwable cause) {
		  super(cause);
	  }
}
