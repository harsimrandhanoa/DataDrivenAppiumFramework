package com.appium.ddf.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import reporting.ExtentManager;

public class GenericKeywords {

	public AppiumDriver<MobileElement> driver;
	public Properties prop;
	public ExtentTest test;
	public SoftAssert softAssert;

	public void openPlatform(String platform) throws MalformedURLException {
		log("Opening The platform " + platform);

		if (platform.equals("Android")) {
			log("In here to open driver" + platform);
			driver = DriverOptions.getAndroidDriver();
			if (driver == null)
				reportFailure("Failing test as driver can't be initialised", true);
			log("driver opened " + platform);
		} else if (platform.equals("IOS")) {
			driver = DriverOptions.getIosDriver();
			threadWait(10);
		}

		threadWait(2);

		// implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void log(String msg) {
		test.log(Status.INFO, msg);
	}

	public void click(String locatorKey) {
		log("clcicking on " + prop.getProperty(locatorKey));
		getElement(locatorKey).click();
	}

	public void type(String locatorKey, String text) {
		log("Typing data " + text + "in element " + prop.getProperty(locatorKey));
		getElement(locatorKey).sendKeys(text);
	}

	public WebElement getElement(String locatorKey) {
		String locator = prop.getProperty(locatorKey);
		WebElement element = null;
		log("Looking for element " + locator + " based on key " + locatorKey);
		try {
			if (locatorKey.contains("xpath")) {
				log("Finding on basis of xpath " + locator);
				element = driver.findElement(By.xpath(locator));
			} else if (locatorKey.contains("id")) {
				log("Finding on basis of id " + locator);
				element = driver.findElement(By.id(locator));
			} else {
				log("Finding on basis of uiAutomator " + locator);
				element = ((FindsByAndroidUIAutomator) driver)
						.findElementByAndroidUIAutomator("text(" + locatorKey + ")");
			}
			return element;
		} catch (Exception e) {
			reportFailure("test failed as given element with loactor " + locator + "can't be found", true);
			return null;

		}
	}

	public void clickOnText(String text) {
		getElementWithText(text).click();
	}

	public WebElement getElementWithText(String text) {
		return ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator("text(" + '"' + text + '"' + ")");
	}

	public void scrollDown(String numberOfTimes) {

		int numberOfScrolls = 1;

		if (numberOfTimes.equals("once"))
			numberOfScrolls = 1;
		else if (numberOfTimes.equals("twice"))
			numberOfScrolls = 2;
		else
			numberOfScrolls = 3;

		((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(" + numberOfScrolls
						+ ");");

	}

	public void scrollIntoView(String text) {

		threadWait(2);
		((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains("
						+ '"' + text + '"' + ").instance(0))");
	}

	public void reportFailure(String failureMsg, boolean stopOnFailure) {
		test.log(Status.FAIL, failureMsg);// failure in extent reports
		if (driver != null)
			takeScreenshot();// put the screenshot in reports
		softAssert.fail(failureMsg);// failure in TestNG reports

		if (stopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
			assertAll();// report all the failures
		}
	}

	public void takeScreenshot() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String screenshotFile = dateFormat.format(date).replaceAll(":", "_") + ".png";
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath + "//" + screenshotFile));
			test.fail(
					"<p><font color=red>" + " Click the below link or check the latest  report folder named "
							+ ExtentManager.screenshotFolderPath + " and then view the screenshot named "
							+ screenshotFile + "</font></p>",
					MediaEntityBuilder
							.createScreenCaptureFromPath(ExtentManager.screenshotFolderPath + "//" + screenshotFile)
							.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.getCurrentTestResult().getTestContext().setAttribute("screenshotTaken", "Y");

	}

	public void assertAll() {
		softAssert.assertAll();
	}

	public void quit() {
		test.log(Status.INFO, "About to quit the app");
	}

	public void threadWait(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
