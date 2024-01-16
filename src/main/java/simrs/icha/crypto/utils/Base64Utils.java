/**
 * Base64Utils.java Jan 12, 2024 2:15:19 PM Tubagus Uun
 * Copyright (c) 2024 PT. Adwa Info Mandiri
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.util.Base64;

/**
 * @author "Tubagus Uun"
 *
 */
public class Base64Utils {

	/**
	 * Encode Base64 dari parameter plaint Text String
	 * @param plaintString
	 * @return byte[]
	 */
	public static byte[] encodeStringToBase64ByteArray(String plaintString) {
		return Base64.getEncoder().encode(plaintString.getBytes());
	}
	
	
	/**
	 * Encode base64 dari parameter byteArray
	 * @param byteArray
	 * @return String
	 */
	public static String encodeByteArrayToBase64String(byte[] byteArray) {
		return Base64.getEncoder().encodeToString(byteArray);
	}

	/**
	 * Decode base64 dari parameter byte Array data yang terencode
	 * @param base64ByteArray
	 * @return
	 */
	public static String decodeBase64ByteArrayoString(byte[] base64ByteArray) {
		return new String(Base64.getDecoder().decode(base64ByteArray));
	}
	
	/**
	 * Decode Base64 dari parameter String data yang terencode
	 * @param base64String
	 * @return
	 */
	public static byte[] decodeBase64StringToByteArray(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}
}
