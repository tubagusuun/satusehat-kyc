/**
 * IOUtilsTest.java Jan 12, 2024 10:44:17 AM Tubagus Uun
 * Copyright (c) 2024 PT. Adwa Info Mandiri
 * All right reserved
 * 
 */
package simrs.icha.crypto.utils;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * @author "Tubagus Uun"
 *
 */
class IOUtilsTest {

	@Test
	void test() throws IOException {
		String isiFile = "ini file isinya";
		String filePath = "src/test/resources/output_coba.txt";
		
		IOUtils.writeFileText(filePath, isiFile);
		
		String isiBacaan = IOUtils.readFileText(filePath);
		System.out.println(isiBacaan);
	}

}
