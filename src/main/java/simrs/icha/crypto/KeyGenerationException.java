/**
 * KeyGenerationException.java Jan 12, 2024 2:59:24 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto;

/**
 * @author "Tubagus Uun"
 *
 */
public class KeyGenerationException extends Exception{

	private static final long serialVersionUID = 1L;

	public KeyGenerationException() {
		super();
	}

	public KeyGenerationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KeyGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeyGenerationException(String message) {
		super(message);
	}

	public KeyGenerationException(Throwable cause) {
		super(cause);
	}

}
