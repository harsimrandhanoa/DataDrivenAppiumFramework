package com.appium.ddf.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reporting.ExtentManager;
import runner.DataUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.appium.ddf.base.ApplicationKeywords;

public class BaseTest {

	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;

	@BeforeSuite(alwaysRun = true)
	public void startAppium() {
		try {
			ServerStarter.startServer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext context)
			throws NumberFormatException, FileNotFoundException, IOException, ParseException {

		if (context.getCurrentXmlTest().getName().equals("Command line test"))
			throw new SkipException("Skipping command line test");

		String dataFilePath = context.getCurrentXmlTest().getParameter("datafilepath");
		String dataFlag = context.getCurrentXmlTest().getParameter("dataflag");
		String iteration = context.getCurrentXmlTest().getParameter("iteration");
		String platform = context.getCurrentXmlTest().getParameter("platform");

		JSONObject data = new DataUtil().getTestData(dataFilePath, dataFlag, Integer.parseInt(iteration));
		context.setAttribute("data", data);

		String runmode = (String) data.get("runmode");

		rep = ExtentManager.getReports(); // made object of rep
		test = rep.createTest(context.getCurrentXmlTest().getName()); // made
																		// object
																		// of
																		// test

		test.log(Status.INFO, "Data " + data.toString());

		test.log(Status.INFO, "Starting Test " + context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "datafilepath " + dataFilePath);
		test.log(Status.INFO, "dataFlag " + dataFlag);
		test.log(Status.INFO, "iteration " + iteration);
		test.log(Status.INFO, "platform " + platform);
		test.log(Status.INFO, "runmode " + runmode);

		context.setAttribute("report", rep); // set rep and test objects in
												// context
		context.setAttribute("test", test);

		if (runmode.equals("N")) {
			test.log(Status.SKIP, "Skipping as runmod is N");
			throw new SkipException("Skipping as runmod is N");
		}

		app = new ApplicationKeywords(test); // 1 app keyword object for entire
												// test -All @Test
		app.setReport(test); // passed the test object created above to
								// ApplicationKeywords Class
		context.setAttribute("app", app); // set object of app
		app.openPlatform(platform); // open app on given platform
		app.log(platform + " opened.Now ready to test");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context) {

		String criticalFailure = (String) context.getAttribute("criticalFailure");
		if (criticalFailure != null && criticalFailure.equals("Y")) {
			throw new SkipException("Critical Failure in Prevoius Tests");// skip
																			// in
																			// testNG
		}

		// Use these variables set in before test in each method

		app = (ApplicationKeywords) context.getAttribute("app");

	}

	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		if (app != null)
			app.quit();
		if (rep != null)
			rep.flush();
	}

}
