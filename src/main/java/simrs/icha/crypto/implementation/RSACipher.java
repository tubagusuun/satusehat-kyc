/**
 * RSACipher.java Jan 14, 2024 3:18:25 AM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.implementation;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import simrs.icha.crypto.CipherConstants;
import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;

/**
 * @author "Tubagus Uun"
 *
 */
public class RSACipher {

	private static RSACipher instance = null;

	private RSACipher() {
		super();
	}

	public static RSACipher getInstance() {
		if (instance == null) {
			instance = new RSACipher();
		}
		return instance;
	}

	/**
	 * Enkripsi data dalam bentuk byte array dengan menggunakan RSA Public Key.
	 * 
	 * @param input
	 * @param publicKey
	 * @return byte[] data yang sudah ter-enkripsi
	 * @throws EncrytionException
	 */
	public byte[] encrypt(byte[] input, PublicKey publicKey) throws EncrytionException {

		Cipher cipher;
		try {
			// 1. Buat object cipher implementasi RSA
			cipher = Cipher.getInstance(CipherConstants.RSA_OAEP_SHA_256_MGF1PADDING);

			OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
					PSource.PSpecified.DEFAULT);

			// 2. Inisialisasi cipher mode encrypt dengan penggunakan public key
			cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParameterSpec);

			// 3. Proses enkripsi data. 
			return cipher.doFinal(input);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new EncrytionException(e);
		}
	}

	/**
	 * Dekripsi data ter-enkripsi dalam bentuk byte array dengan menggunakan RSA Private Key.
	 * 
	 * @param encryptedDataInByte
	 * @param privateKey
	 * @return byte[] data yang sudah ter-dekripsi
	 * @throws DecryptionException
	 */
	public byte[] decrypt(byte[] encryptedDataInByte, PrivateKey privateKey) throws DecryptionException {
		Cipher cipher;

		try {
			// 1. Buat object cipher implementasi RSA
			cipher = Cipher.getInstance(CipherConstants.RSA_OAEP_SHA_256_MGF1PADDING);
			
			OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
					PSource.PSpecified.DEFAULT);

			// 2. Inisialisasi cipher mode decrypt dengan penggunakan private key
			cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParameterSpec);

			// 3. Proses dekripsi data. 
			return cipher.doFinal(encryptedDataInByte);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new DecryptionException(e);
		}
	}

}
