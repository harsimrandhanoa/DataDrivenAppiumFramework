package com.appium.ddf.base;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords {

	public ApplicationKeywords(ExtentTest test) {
		this.test = test;
		String path = System.getProperty("user.dir") + "//src//test//resources//env.properties";
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
			fs = new FileInputStream(path);
			prop.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		softAssert = new SoftAssert();
	}

	public void setReport(ExtentTest test) {
		this.test = test;
	}

}
