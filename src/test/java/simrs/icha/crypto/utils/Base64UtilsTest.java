/**
 * Base64UtilsTest.java Jan 12, 2024 2:24:44 PM Tubagus Uun
 * Copyright (c) 2024 PT. Adwa Info Mandiri
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.security.SecureRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author "Tubagus Uun"
 *
 */
class Base64UtilsTest {

	@Test
	void test() {
		String coba = "ini coba aja";
		System.out.println("data awal : " + coba);
		byte[] encodedByte = Base64Utils.encodeStringToBase64ByteArray(coba);
		System.out.println("hasi encode : " + new String(encodedByte));

		String decodedString = Base64Utils.decodeBase64ByteArrayoString(encodedByte);
		System.out.println("hasil decode : " + decodedString);

		byte[] cobabyte = new byte[32];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(cobabyte);
		System.out.println("byte hasil random : " + new String(cobabyte));

		String encodedString = Base64Utils.encodeByteArrayToBase64String(cobabyte);

		System.out.println("encoded String : " + encodedString);

		byte[] resultByte = Base64Utils.decodeBase64StringToByteArray(encodedString);
		
		Assertions.assertArrayEquals(cobabyte, resultByte);
		
	}

}
