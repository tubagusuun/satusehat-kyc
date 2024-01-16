/**
 * CipherConstants.java Jan 13, 2024 12:48:15 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto;

/**
 * @author "Tubagus Uun"
 *
 */
public class CipherConstants {

	public static final String AES = "AES";
	public static final String CIPHER_TRANSFORMATION_GCM = "AES/GCM/NoPadding";
	public static final int IV_LENGTH_BYTE = 12;
	public static final int TAG_LENGTH_BIT = 128;

	public static final String RSA = "RSA";
	public static final int RSA_KEY_SIZE = 2048;
	public static final String RSA_OAEP_SHA_256_MGF1PADDING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

}
