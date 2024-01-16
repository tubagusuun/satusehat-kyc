/**
 * Kyc.java Jan 16, 2024 12:33:51 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 */
package simrs.icha.crypto.satusehat.kyc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;
import simrs.icha.crypto.CipherConstants;
import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;
import simrs.icha.crypto.implementation.AESCipher;
import simrs.icha.crypto.implementation.RSACipher;
import simrs.icha.crypto.utils.AESUtil;
import simrs.icha.crypto.utils.Base64Utils;
import simrs.icha.crypto.utils.IOUtils;
import simrs.icha.crypto.utils.RSAUtil;

/**
 * @author "Tubagus Uun"
 *
 */
@Getter
public class Kyc {

	private SecretKey secretKey;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	private String pubKeyFormat;

	private PublicKey satuSehatPublicKey;

	private String beginMsgTag = "-----BEGIN ENCRYPTED MESSAGE-----\r\n";
	private String endMsgTag = "-----END ENCRYPTED MESSAGE-----";

	public Kyc() {
		super();
	}

	public Kyc(String secretKeyPath, String publicKeyPath, String privateKeyPath, String satuSehatPublicKeyPath)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		super();

		String aesKeyString = IOUtils.readFileText(secretKeyPath);
		secretKey = AESUtil.generateAESSecretKey(aesKeyString);

		publicKey = RSAUtil.loadPublicKeyFromTextFile(publicKeyPath);
		privateKey = RSAUtil.loadPrivateKeyFromTextFile(privateKeyPath);

		String pubString = Base64Utils.encodeByteArrayToBase64String(publicKey.getEncoded());
		pubKeyFormat = RSAUtil.beginPubKeyTag + "\n" + RSAUtil.chunkSplit(pubString) + RSAUtil.endPubKeyTag;

		satuSehatPublicKey = RSAUtil.loadPublicKeyFromTextFile(satuSehatPublicKeyPath);

	}

	private String formatMsg(String data) {
		return beginMsgTag + data + endMsgTag;
	}

	private String parseFormatMsg(String data) {
		return data.substring(beginMsgTag.length() - 2, data.length() - endMsgTag.length());
	}

	public String encrypt(String data) throws EncrytionException {

		// encrypt data menggunakana aes key icha
		byte[] encryptedDataInByte = AESCipher.getInstance().encrypt(data.getBytes(), secretKey);

		// encrypt AES Key menggunakan RSA Public Key punya satu sehat
		byte[] encodedNewSecretKeyInByte = secretKey.getEncoded();
		encodedNewSecretKeyInByte = RSACipher.getInstance().encrypt(encodedNewSecretKeyInByte, satuSehatPublicKey);

		// Gabungkan byte array enryptedNewSecretKeyInByte dan encryptedDataInByte
		byte[] encryptedDataConcateInByte = AESUtil.concatGCM(encodedNewSecretKeyInByte, encryptedDataInByte);

		// Data gabungan dijadikan string base 64
		String encryptedDataConcateInString = Base64Utils.encodeByteArrayToBase64String(encryptedDataConcateInByte);
		
		return formatMsg(encryptedDataConcateInString);
		
	}
	
	public String decrypt(String data) throws DecryptionException {
		
		String encryptedDataConcatInString = parseFormatMsg(data);
		byte[] encryptedDataConcatInByte = Base64Utils.decodeBase64StringToByteArray(encryptedDataConcatInString);
		
		Map<String, byte[]> byteMap = parseByteData(encryptedDataConcatInByte);
		
		// ambil byte AES secretkey
		byte[] encryptedSecretKeyInByte = byteMap.get("key");
		
		// ambil byte data
		byte[] encryptedMsgKeyInByte = byteMap.get("data");
		
		// decrypt AES SecretKey Satu Sehat menggunakan RSA Private Key SIMRS ICHA
		byte[] encodedSecretKey = RSACipher.getInstance().decrypt(encryptedSecretKeyInByte, privateKey);
		
		// buat Object secretkey satusehat
		SecretKey satuSehatSecretKey = new SecretKeySpec(encodedSecretKey, CipherConstants.AES);
		
		// decrypt data
		byte[] dataSatuSehatByte = AESCipher.getInstance().decrypt(encryptedMsgKeyInByte, satuSehatSecretKey);
		
		String dataSatuSehat = new String(dataSatuSehatByte);
		
		return dataSatuSehat;
	}
	
	private Map<String, byte[]> parseByteData(byte[] input){
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(input);
		
		int secretKeySize = CipherConstants.RSA_KEY_SIZE/8;
		int msgSize = input.length - secretKeySize;
		
		byte[] byteKey = new byte[secretKeySize];
		byteBuffer.get(byteKey);

		byte[] byteMsg = new byte[msgSize];
		byteBuffer.get(byteMsg);
		
		Map<String, byte[]> result = new HashMap<>();
		result.put("key", byteKey);
		result.put("data", byteMsg);

		return result;
	}

}
