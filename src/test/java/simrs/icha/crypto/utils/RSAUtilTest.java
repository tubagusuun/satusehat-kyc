/**
 * RSAUtilTest.java Jan 14, 2024 2:07:55 AM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

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
class RSAUtilTest {

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
	void generateKeyPair() throws KeyGenerationException {
		RSAUtil.generateKeyPair(pathFile);
		System.out.println("private key dan public key berhasil dibuat di " + pathFile);
	}

	@Test
	@Order(2)
	void loadPrivateKeyFromTextFile()
			throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		PrivateKey pvt = RSAUtil.loadPrivateKeyFromTextFile(pathFile + "/rsa_pvt.txt");
		if (pvt != null)
			System.out.println("Private key berhasil terload");
	}

	@Test
	@Order(3)
	void loadPublicKeyFromTextFile()
			throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		PublicKey pub = RSAUtil.loadPublicKeyFromTextFile(pathFile + "/rsa_pub.txt");
		if (pub != null)
			System.out.println("Public key berhasil terload");
	}

}
