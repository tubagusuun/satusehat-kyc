/**
 * AESCipherTest.java Jan 13, 2024 1:36:38 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.implementation;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;
import simrs.icha.crypto.utils.AESUtil;
import simrs.icha.crypto.utils.Base64Utils;
import simrs.icha.crypto.utils.IOUtils;

/**
 * @author "Tubagus Uun"
 *
 */
class AESCipherTest {

	private static SecretKey secretKey;
	private static String pathFile;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pathFile = "src/test/resources";
		String aeskey = IOUtils.readFileText(pathFile + "/aes_key.txt");
		secretKey = AESUtil.generateAESSecretKey(aeskey);

	}

	@ParameterizedTest
	@ValueSource(strings = {"testing", "coba aja nih"})
	void test(String input) throws EncrytionException, DecryptionException {
		System.out.println(input);
		byte[] encryptedByte = AESCipher.getInstance().encrypt(input.getBytes(), secretKey);
		String encryptedText = Base64Utils.encodeByteArrayToBase64String(encryptedByte);
		System.out.println(encryptedText);
		
		byte[] encryptedByte2 = Base64Utils.decodeBase64StringToByteArray(encryptedText);
		byte[] decriptedByte = AESCipher.getInstance().decrypt(encryptedByte2, secretKey);
		String nilaiawal = new String(decriptedByte);
		System.out.println(nilaiawal);
		System.out.println("========================================================");
	}

}
