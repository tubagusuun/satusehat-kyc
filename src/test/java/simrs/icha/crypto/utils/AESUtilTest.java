/**
 * AESUtilTest.java Jan 13, 2024 1:31:17 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import simrs.icha.crypto.KeyGenerationException;

/**
 * @author "Tubagus Uun"
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class AESUtilTest {
	
	private static String pathFile;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pathFile = "src/test/resources";
	}

	@Test
	@Order(1)
	void generateAESSecretKeyInFile() throws KeyGenerationException {
		AESUtil.generateAESSecretKeyInFile(256, pathFile);
		System.out.println("AES KEY berhasil dibuat di "+ pathFile);
	}

	@Test
	@Order(2)
	void generateAESSecretKey() throws KeyGenerationException, FileNotFoundException, IOException {
		String aesKeyString = IOUtils.readFileText(pathFile+"/aes_key.txt");
		SecretKey aesKey = AESUtil.generateAESSecretKey(aesKeyString);
		System.out.println("aes key berhasil dimuat");
		String keyString = Base64Utils.encodeByteArrayToBase64String(aesKey.getEncoded());
		assertEquals(aesKeyString, keyString);
	}
	
	
}
