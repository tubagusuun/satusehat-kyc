/**
 * IOUtils.java Jan 12, 2024 10:33:37 AM Tubagus Uun
 * Copyright (c) 2024 PT. Adwa Info Mandiri
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author "Tubagus Uun"
 *
 */
public class IOUtils {

	/**
	 * fungsi buat baca file text
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFileText(String filePath) throws FileNotFoundException, IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
	
		try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr)) {
			
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		
		return stringBuilder.toString();
	}

	
	/**
	 * buat file text
	 * @param filePath
	 * @param msg
	 * @throws IOException
	 */
	public static void writeFileText(String filePath, String msg) throws IOException {
		try (Writer wo = new FileWriter(filePath)){
			wo.write(msg);
		}
	}
	
}
