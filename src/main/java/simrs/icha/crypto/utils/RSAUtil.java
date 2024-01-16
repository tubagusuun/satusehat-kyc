/**
 * RSAUtil.java Jan 14, 2024 1:45:48 AM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import simrs.icha.crypto.CipherConstants;
import simrs.icha.crypto.KeyGenerationException;

/**
 * @author "Tubagus Uun"
 *
 */
public class RSAUtil {

	public static final String beginPrivKeyTag = "-----BEGIN PRIVATE KEY-----";
	public static final String endPrivKeyTag = "-----END PRIVATE KEY-----";
	public static final String beginPubKeyTag = "-----BEGIN PUBLIC KEY-----";
	public static final String endPubKeyTag = "-----END PUBLIC KEY-----";

	/**
	 * Generate RSA Keypair (Object pasangan key) 2048 bit
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CipherConstants.RSA);
		keyPairGenerator.initialize(CipherConstants.RSA_KEY_SIZE);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * Generate pasangan key RSA (public key dan private key) dimasukan kedalam file
	 * terpisah masing-masing. Ada 2 file yang akan tergenerate, file khusus public
	 * key dan file khusus private key.
	 * 
	 * @param pathFile
	 * @throws KeyGenerationException
	 */
	public static void generateKeyPair(String pathFile) throws KeyGenerationException {
		try {
			KeyPair keyPair = generateKeyPair();
			PrivateKey pvt = keyPair.getPrivate();
			PublicKey pub = keyPair.getPublic();

			String pvtString = Base64Utils.encodeByteArrayToBase64String(pvt.getEncoded());
			String pvtFormat = beginPrivKeyTag + "\n" + chunkSplit(pvtString) + endPrivKeyTag;
			IOUtils.writeFileText(pathFile + "/rsa_pvt.txt", pvtFormat);

			String pubString = Base64Utils.encodeByteArrayToBase64String(pub.getEncoded());
			String pubFormat = beginPubKeyTag + "\n" + chunkSplit(pubString) + endPubKeyTag;
			IOUtils.writeFileText(pathFile + "/rsa_pub.txt", pubFormat);

		} catch (Exception e) {
			throw new KeyGenerationException(e);
		}
	}

	/**
	 * Memuat RSA Private Key dari text file
	 * 
	 * @param filePath
	 * @return PrivateKey
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey loadPrivateKeyFromTextFile(String filePath)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		String privateKeyPEM = IOUtils.readFileText(filePath);

		privateKeyPEM = privateKeyPEM.replace(beginPrivKeyTag, "").replace("\n", "");
		privateKeyPEM = privateKeyPEM.replace(endPrivKeyTag, "");

		return loadPrivateKeyFromStringKey(privateKeyPEM);
	}
	
	/**
	 * Memuat RSA Private Key dari String <code>key</code>. <br>
	 * Key yang dijadikan paramater harus sudah bersih dari penanda (sudah
	 * difilter).<br>
	 * Penanda disini seperti : -----BEGIN RSA PRIVATE KEY----- , -----END RSA
	 * PRIVATE KEY-----.
	 * 
	 * @param key
	 * @return PrivateKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey loadPrivateKeyFromStringKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Base64Utils.decodeBase64StringToByteArray(key);
		return loadPrivateKeyFromByte(bytes);
	}
	
	/**
	 * Memuat RSA Private Key dari <code>bytes</code>.
	 * 
	 * @param bytes
	 * @return PrivateKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey loadPrivateKeyFromByte(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance(CipherConstants.RSA);
		PrivateKey pvt = kf.generatePrivate(ks);
		return pvt;
	}

	/**
	 * Memuat RSA Public Key dari text file
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey loadPublicKeyFromTextFile(String filePath)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		String publicKeyPEM = IOUtils.readFileText(filePath);

		publicKeyPEM = publicKeyPEM.replace(beginPubKeyTag, "").replace("\n", "");
		publicKeyPEM = publicKeyPEM.replace(endPubKeyTag, "");

		return loadPublicKeyFromStringKey(publicKeyPEM);
	}
	
	/**
	 * Memuat RSA Public Key dari String <code>key</code>. <br>
	 * Key yang dijadikan paramater harus sudah bersih dari penanda (sudah
	 * difilter).<br>
	 * Penanda disini seperti : -----BEGIN PUBLIC KEY----- , -----END PUBLIC
	 * KEY-----.
	 * @param key
	 * @return PublicKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey loadPublicKeyFromStringKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Base64Utils.decodeBase64StringToByteArray(key);
		return loadPublicKeyFromByte(bytes);
	}
	
	/**
	 * Memuat RSA Public Key dari <code>bytes</code>
	 * @param bytes
	 * @return PublicKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey loadPublicKeyFromByte(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance(CipherConstants.RSA);
		PublicKey pub = kf.generatePublic(ks);
		return pub;
	}

	/**
	 * Ngebagusin string key biar enak diliat
	 * 
	 * @param base64String
	 * @return
	 */
	public static String chunkSplit(String base64String) {
		int chunkSize = 64;
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < base64String.length(); i += chunkSize) {
			int endIndex = Math.min(i + chunkSize, base64String.length());
			stringBuilder.append(base64String, i, endIndex);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

}
