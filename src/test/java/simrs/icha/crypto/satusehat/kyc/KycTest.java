/**
 * KycTest.java Jan 16, 2024 1:09:20 PM Tubagus Uun PT. Adwa Info Mandiri
 * Copyright (c) 2024 
 * All right reserved
 * 
 * PT.Adwa Info Mandiri PROPIETARY/CONFIDENTIAL. Use is subject to
 * license terms
 */
package simrs.icha.crypto.satusehat.kyc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import simrs.icha.crypto.DecryptionException;
import simrs.icha.crypto.EncrytionException;

/**
 * @author "Tubagus Uun"
 *
 */
class KycTest {

	private static Kyc kyc;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String pathFile = "src/test/resources";
		kyc = new Kyc(pathFile + "/aes_key.txt", pathFile + "/rsa_pub.txt", pathFile + "/rsa_pvt.txt",
				pathFile + "/rsa_public_satusehat.pub");
	}


	@Test
	@Disabled
	void kirim() throws EncrytionException, IOException {
		ObjData obj = new ObjData("JAJANG", "123123123", kyc.getPubKeyFormat());

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(obj);

		String data = kyc.encrypt(json);
		System.out.println(data);
		
		sendKyc(data);
	}
	
	
	@ParameterizedTest
	// nanti ini ganti aja sesuai responsenya masing2
	@ValueSource(strings = {"-----BEGIN ENCRYPTED MESSAGE-----"
			+ "KTiZF9wUor8BwEi9oG4zLhC0w18LckQDgsJ1jPfsLQPzZDH0HQTkbO+iUJccNA5DR4"
			+ "YaXzTQdvgO+jTEgEynxRzPrcmyyzjGcfelefi3mc4+rwNtZxy5gsOtz68RJBp4WEBe"
			+ "wE+gIKxv++SAYhXIECxvlTTRwt5nVwEMr1U4X9Wv7Emgtp/Ca0brCPZuxJUsp1RnQr5"
			+ "X7I/0TW/c4OMBECaq4GpDOUKtfB2xs36QC9svCZFEWrpWswf3AIL4FR1UYo6jm0an//0R"
			+ "nr4mhv5oNS496OqEjA3dD4/jEEQ3qV2MqUhMOUCKNIbOTlfuqwC0VemJxrfXuxIPsukV5P9"
			+ "Kt9AILJCjqgRuyI859gbwDNSWQL5aoAIzEKcI0YUUGcLc/z7ytEq+9UIV8MZrD1HJaxTqrQFjLTSO"
			+ "KKe8Ig+WXRZoAmztymmE1UE7eyqbl6Skxgd/WYJk+d7Mcp6EgidlJJP5fAnaKs6LCF56Jx1gSshxvw"
			+ "c27twsB1vkWeOycyG1/V7XH4Xs9GFgEYeqNGITNKFerI0Z3FA8BMwOiAr5kHqBmJGniwc2DPTuNtS0AZJdwgs"
			+ "f1ePEuoI0LliKtfEZbBvX9Rau17XyGHUAZrNPfWuNfGZHuuCKF9b3+eEJomTnYgDo5zo4TAyIBm6LHPEGFoaIUu"
			+ "2bCADb7b1jOik5t2/Omsdtp+1SkFC4tfCYwIo7/s0="
			+ "-----END ENCRYPTED MESSAGE-----"})
//	@Disabled
	// uncoment aja @Disabled-nya kalo mau ujicoba dekrypt
	void terima(String input) throws DecryptionException {
		System.out.println("terima :");
		String data = kyc.decrypt(input);
		System.out.println(data);
	}

	
	private static void sendKyc(String data) throws IOException {
		
		// ini nanti dirubah ya... sesuai dengan token masing2. karena pasti exired, ini hanya ujicoba saja.
		String access_token = "Adt9ioFlWQCHzSDidWpg4AnUbNki";
		
		String url = "https://api-satusehat.kemkes.go.id/kyc/v1/generate-url";
		
		
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Authorization", "Bearer "+access_token);

		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(data.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request did not work.");
		}
	}
}
