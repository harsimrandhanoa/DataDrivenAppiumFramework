package reporting;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	static ExtentReports reports;
	public static String screenshotFolderPath;

	public static ExtentReports getReports() {
		if (reports == null) {// first test

			reports = new ExtentReports();
			// init the report folder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String reportsFolder = dateFormat.format(date).replaceAll(":", "_");

			String screenshotsFolder = reportsFolder + "//screenshots";

			screenshotFolderPath = System.getProperty("user.dir") + "//reports//" + screenshotsFolder;
			String reportFolderPath = System.getProperty("user.dir") + "//reports//" + reportsFolder;
			File f = new File(screenshotFolderPath);
			f.mkdirs();// create dynamic report folder name + screenshots

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
			sparkReporter.config().setReportName("Data driven Appium Framework");
			sparkReporter.config().setDocumentTitle("Data driven Appium framework Reports");
			/*
			 * sparkReporter.config().setTheme(Theme.STANDARD);
			 * sparkReporter.config().setEncoding("utf-8");
			 */

			reports.attachReporter(sparkReporter);
		}

		return reports;

	}

}