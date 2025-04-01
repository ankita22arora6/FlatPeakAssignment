package com.FlatPeak.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

	public static Properties loadProperties(String fileName) throws IOException
	{
		Properties prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/"+ fileName);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
}
