/**
 * AESCipher.java Jan 13, 2024 12:58:36 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.implementation;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import simrs.icha.crypto.CipherConstants;
import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;
import simrs.icha.crypto.utils.AESUtil;

/**
 * @author "Tubagus Uun"
 *
 */
public final class AESCipher {

	private static AESCipher instance = null;
	private ThreadLocal<Cipher> cipherWrapper = new ThreadLocal<>();

	private AESCipher() {
		super();
	}

	public static AESCipher getInstance() {
		if (instance == null) {
			instance = new AESCipher();
		}
		return instance;
	}

	public Cipher getCipher() throws IllegalArgumentException {
		Cipher cipher = cipherWrapper.get();
		if (cipher == null) {
			try {
				cipher = Cipher.getInstance(CipherConstants.CIPHER_TRANSFORMATION_GCM);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new IllegalStateException("Tidak bisa mendapatkan object cipher");
			}

			cipherWrapper.set(cipher);
			return cipherWrapper.get();
		} else {
			return cipher;
		}
	}

	/**
	 * Enkripsi data dalam bentuk byte array dengan menggunakan AES secret key
	 * 
	 * @param input
	 * @param secretKey
	 * @return byte[] data yang sudah ter-enkripsi
	 * @throws EncrytionException
	 */
	public byte[] encrypt(byte[] input, SecretKey secretKey) throws EncrytionException {
		Cipher cipher;

		try {
			// 1 ambil cipher
			cipher = getCipher();
			
			// 2. Menginisialisasi byte array iv (Initialization Vector) atau nilai
			// pseudo-random
			byte[] iv = new byte[CipherConstants.IV_LENGTH_BYTE];

			// 3. Menggunakan object secure random untuk menggenerate nilai iv
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.nextBytes(iv);

			// 4. Buat spesifikasi algoritma GCM untuk dipakai dalam proses enkripsi.
			// spesifikasi algoritma yang dipakai adalah GCMParameterSpec. 
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(CipherConstants.TAG_LENGTH_BIT, iv);

			// 5. Inisialisasi cipher
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

			// 6. Proses enkripsi data. 
			// Dikarenakan menggunakan GCMParameterSpec, 
			// hasil dari proses enkripsi ini authentication tag sudah ditambahkan pada posisi paling akhir.
			// untuk mendapatkan tag bisa dengan menggunakan : 
			// Arrays.copyOfRange(ciphertext, ciphertext.length - (tagSize / Byte.SIZE), ciphertext.length)
			byte[] encryptedDataInByte = cipher.doFinal(input);

			// 7. Concat byte array iv dengan data yang sudah ter-encrypt
			return AESUtil.concatGCM(iv, encryptedDataInByte);

		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new EncrytionException(e);
		}
	}

	/**
	 * Dekripsi data ter-enkripsi dalam bentuk byte array dengan menggunakan AES secret key
	 * 
	 * @param input
	 * @param secretKey
	 * @return byte[] data yang sudah ter-dekripsi
	 * @throws DecryptionException
	 */
	public byte[] decrypt(byte[] input, SecretKey secretKey) throws DecryptionException {
		
		try {
			// 1. parse byte data
			Map<String, byte[]> resbyte = AESUtil.parseGCM(input);

			byte[] iv = resbyte.get("iv");
			byte[] encryptedTextInbyte = resbyte.get("data");
			
			// 2. Ambil Object cipher
			Cipher cipher = getCipher();
			
			// 3. Buat spesifikasi algoritma GCM untuk dipakai dalam proses dekripsi.
			// spesifikasi algoritma yang dipakai adalah GCMParameterSpec. 
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(CipherConstants.TAG_LENGTH_BIT, iv);

			// 4. Inisialisasi cipher
			cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
			
			// 5. Proses dekripsi data.
			// Dikarenakan menggunakan GCMParameterSpec, 
			// hasil dari proses dekripsi ini authentication tag sudah terhandle.
			return cipher.doFinal(encryptedTextInbyte);
			
		} catch (IllegalArgumentException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new DecryptionException(e);
		}
	}

}
