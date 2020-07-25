package com.tingge.Until;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUntil {
    public static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = new FileInputStream(new File("src/test/resources/Config.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getExcelPath(){
        return properties.getProperty("excel.path");
    }

}
