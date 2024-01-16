/**
 * RSACipherTest.java Jan 14, 2024 3:32:38 AM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.implementation;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;
import simrs.icha.crypto.utils.Base64Utils;
import simrs.icha.crypto.utils.RSAUtil;

/**
 * @author "Tubagus Uun"
 *
 */
class RSACipherTest {

	private static String pathFile;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pathFile = "src/test/resources";
		publicKey = RSAUtil.loadPublicKeyFromTextFile(pathFile + "/rsa_pub.txt");
		privateKey = RSAUtil.loadPrivateKeyFromTextFile(pathFile+"/rsa_pvt.txt");
	}

	@ParameterizedTest
	@ValueSource(strings = {"testing", "coba aja nih!", "123123123"})
	void test(String input) throws EncrytionException, DecryptionException {
		System.out.println(input);
		byte[] encryptedByte = RSACipher.getInstance().encrypt(input.getBytes(), publicKey);
		
		System.out.println("panjang data yang terenkripsi = "+encryptedByte.length);
		
		String encryptedText = Base64Utils.encodeByteArrayToBase64String(encryptedByte);
		
		System.out.println(encryptedText);
		
		byte[] encryptedByte2 = Base64Utils.decodeBase64StringToByteArray(encryptedText);
		byte[] decryptedByte = RSACipher.getInstance().decrypt(encryptedByte2, privateKey);
		
		System.out.println(new String(decryptedByte));
		System.out.println("==============================================================");
		
		
	}

}
