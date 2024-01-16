/**
 * EncrytionException.java Jan 12, 2024 2:56:39 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto;

/**
 * @author "Tubagus Uun"
 *
 */
public class EncrytionException extends Exception{

	private static final long serialVersionUID = 1L;

	public EncrytionException() {
		super();
	}

	public EncrytionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EncrytionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncrytionException(String message) {
		super(message);
	}

	public EncrytionException(Throwable cause) {
		super(cause);
	}

}
