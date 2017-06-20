package com.ly.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {
	private static Properties prop  ;
	private PropUtils(){};
	static{
		prop =new Properties();
		try {
			prop.load(new FileInputStream(PropUtils.class.getClassLoader().getResource("config.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Properties getProp(){
		return prop;
	}
	public static String getProp(String key){
		return prop.getProperty(key);
	}
	
}
