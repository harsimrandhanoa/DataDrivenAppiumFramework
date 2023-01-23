package com.appium.ddf.base;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class DriverOptions {
	
	public static AndroidDriver<MobileElement> getAndroidDriver() throws MalformedURLException {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName","Galaxy s5");
        cap.setCapability("udid", "244b1755");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "6.0.1");
        cap.setCapability("appPackage","com.rediff.android.shopping");
        cap.setCapability("appActivity", "com.rediff.android.shopping.ui.SplashScreenActivity");

         try{
        AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
              return driver;
          }
         
         catch (Exception e){
        	 return null;
         }
       }
	
	public static IOSDriver<MobileElement> getIosDriver() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
        IOSDriver<MobileElement> driver = new IOSDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),cap);
		return driver;
	}

}
