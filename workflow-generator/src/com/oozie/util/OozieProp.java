package com.oozie.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class OozieProp {

	public static Properties Getproperties() {
		Properties prop = new Properties();
		// String[] args = OozieMain.getArgs();

try (InputStream input =new  FileInputStream(new File("resource.properties"))) {

//		try (InputStream input = OozieProp.class.getClassLoader().getResourceAsStream("resource.properties")) {

			if (input == null) {
				System.out.println("Unable to find resource.properties");
			}

			prop.load(input);

			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;

	}

	public static String getFileDataAsString(String file) throws IOException {
		 InputStream input =new FileInputStream(new File(file));
	//	InputStream input = OozieProp.class.getClassLoader().getResourceAsStream(file);
		String data = convertInputStreamToString(input);
		return data;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString(StandardCharsets.UTF_8.name());

	}

	
}
