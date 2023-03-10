package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.appium.ddf.base.ApplicationKeywords;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyTestNGListener implements ITestListener {

	public void onTestFailure(ITestResult result) {
		String screenShotTaken;

		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("test");
		test.log(Status.INFO, " in test falure");

		Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
		ApplicationKeywords app = (ApplicationKeywords) result.getTestContext().getAttribute("app");
		test.log(Status.FAIL, "Test failed - " + result.getName());
		if (result.getTestContext().getAttribute("screenshotTaken") != null) {
			screenShotTaken = (String) result.getTestContext().getAttribute("screenshotTaken");
		}
		if ((app != null) && result.getTestContext().getAttribute("screenshotTaken") != null
				&& !result.getTestContext().getAttribute("screenshotTaken").equals("Y"))
			app.takeScreenshot();
	}

	public void onTestSuccess(ITestResult result) {

		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("test");
		test.log(Status.INFO, " in test pass");
		test.log(Status.PASS, "Test Passed - " + result.getName());
	}

	public void onTestSkipped(ITestResult result) {

		String criticalFailure = (String) result.getTestContext().getAttribute("criticalFailure");
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("test");

		test.log(Status.INFO, " in test skip");

		if (criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Test  " + result.getName() + " skipped due to critical failure in previous test");
		}

		else {
			test.log(Status.SKIP, "Test  " + result.getName() + " skipped due to unknown reason");
		}

	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}