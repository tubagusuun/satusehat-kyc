/**
 * DecryptionException.java Jan 12, 2024 2:58:28 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto;

/**
 * @author "Tubagus Uun"
 *
 */
public class DecryptionException extends Exception{

	private static final long serialVersionUID = 1L;

	public DecryptionException() {
		super();
	}

	public DecryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DecryptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DecryptionException(String message) {
		super(message);
	}

	public DecryptionException(Throwable cause) {
		super(cause);
	}

}
