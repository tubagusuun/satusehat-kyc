/**
 * AESUtil.java Jan 13, 2024 12:43:39 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import simrs.icha.crypto.CipherConstants;
import simrs.icha.crypto.KeyGenerationException;

/**
 * @author "Tubagus Uun"
 *
 */
public class AESUtil {

	/**
	 * Generate AES Secret Key dengan menginisialisasi keysize-nya
	 * 
	 * @param keySize
	 * @return SecretKey
	 * @throws KeyGenerationException
	 */
	public static SecretKey generateAESSecretKey(int keySize) throws KeyGenerationException {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(CipherConstants.AES);
			keyGenerator.init(keySize);
			SecretKey aesKey = keyGenerator.generateKey();
			return aesKey;
		} catch (NoSuchAlgorithmException e) {
			throw new KeyGenerationException(e);
		}
	}

	/**
	 * Generate AES Secret Key kemudian disimpan kedalam file
	 * 
	 * @param keySize
	 * @param pathFile
	 * @throws KeyGenerationException
	 */
	public static void generateAESSecretKeyInFile(int keySize, String pathFile) throws KeyGenerationException {
		try {
			SecretKey aesKey = generateAESSecretKey(keySize);
			String encodedAesKey = Base64Utils.encodeByteArrayToBase64String(aesKey.getEncoded());
			IOUtils.writeFileText(pathFile + "/aes_key.txt", encodedAesKey);
		} catch (IOException e) {
			throw new KeyGenerationException(e);
		}
	}
	
	/**
	 * Generate AES Secret Key dengan parameter String Key yang terencode base64
	 * 
	 * @param encodedAESKey
	 * @return SecretKey
	 */
	public static SecretKey generateAESSecretKey(String encodedAESKey) {
		byte[] aesKey = Base64Utils.decodeBase64StringToByteArray(encodedAESKey);
		SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, CipherConstants.AES);
		return secretKeySpec;
	}

	/**
	 * Menggabungkan byte iv (Initialization Vector) dan data yang telah
	 * terenkripsi.
	 * 
	 * @param iv
	 * @param encryptedText
	 * @return
	 */
	public static byte[] concatGCM(byte[] iv, byte[] encryptedbyte) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedbyte.length);
		byteBuffer.put(iv);
		byteBuffer.put(encryptedbyte);
		return byteBuffer.array();
	}
	
	/**
	 * Memisahkan byte iv (Initialization Vector) dari data gabungan
	 * @param encryptedByte
	 * @return
	 */
	public static Map<String, byte[]> parseGCM(byte[] encryptedByte){
		// 1. Gunakan object ByteBuffer untuk menampung byte array data ter-encrypt
		ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedByte);
		
		// 2. Inisialisasi byte arrray iv (Initialization Vector) dengan ukuran menggunakan nilai ivlength 
		byte[] iv = new byte[CipherConstants.IV_LENGTH_BYTE];
		
		// 3. Baca byte array nilai iv sekaligus rubah posisi buffer
		byteBuffer.get(iv);
		
		// 4. Inisialisasi byte arraya data ter-encrypt (tag sudah terinclude didalamnya)
		byte[] encryptedTextInByte = new byte[byteBuffer.remaining()];
		
		// 5. Baca byte array nilai data ter-encrypt (tag sudah terinclude didalamnya)
		byteBuffer.get(encryptedTextInByte);
		
		// 7. Masukan iv dan data kedalam Map 
		Map<String, byte[]> result = new HashMap<>();
		result.put("iv", iv);
		result.put("data", encryptedTextInByte);
		return result;
	}
}
