package runner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class JSONRunner {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

		Map<String, String> classMethods = new DataUtil().loadClassMethods();

		JSONRunnerHelper jsonRunnerHelper = new JSONRunnerHelper();

		JSONObject json = jsonRunnerHelper.getTestConfigObject();

		TestNGRunner testNG = jsonRunnerHelper.createTestNgRunner(json);// create
																		// runner

		JSONArray platformsArray = jsonRunnerHelper.getPlatformsArray(json);

		JSONArray testSuites = jsonRunnerHelper.getSuitesArray(json);

		for (int pId = 0; pId < platformsArray.size(); pId++) {

			String platform = (String) platformsArray.get(pId);

			for (int tsId = 0; tsId < testSuites.size(); tsId++) {

				Map<String, String> testSuiteData = jsonRunnerHelper
						.getTestSuiteData((JSONObject) testSuites.get(tsId));

				if (!testSuiteData.isEmpty()) {
					jsonRunnerHelper.addSuiteToRunner(testNG, testSuiteData);

					if (tsId == 0)
						jsonRunnerHelper.addTestNGListener(testNG);

					JSONArray suiteTestCases = jsonRunnerHelper.getTestCasesFromSuite(testSuiteData);

					for (int sId = 0; sId < suiteTestCases.size(); sId++) {

						Map<String, Object> testCaseData = jsonRunnerHelper
								.getTestCaseData((JSONObject) suiteTestCases.get(sId));

						JSONArray executions = (JSONArray) testCaseData.get("executions");

						for (int eId = 0; eId < executions.size(); eId++) {

							Map<String, Object> executionData = jsonRunnerHelper
									.getExecutionData((JSONObject) executions.get(eId));

							String tRunMode = (String) executionData.get("runmode");

							if (tRunMode != null && tRunMode.equals("Y")) {
								String executionName = (String) executionData.get("name");
								String dataflag = (String) executionData.get("dataFlag");

								String testDataJsonFilePath = System.getProperty("user.dir")
										+ "//src//test//resources//json//" + testSuiteData.get("jsonFilePath");

								int testdatasets = new DataUtil().getTestDataSets(testDataJsonFilePath, dataflag);

								for (int dsId = 1; dsId <= testdatasets; dsId++) {
									JSONArray methods = (JSONArray) executionData.get("methods");

									testNG.addTest(testCaseData.get("testName") + "-" + executionName + "-It." + dsId
											+ "-" + platform); // adding a test
																// case to suite

									testNG.addTestParameter("datafilepath", testDataJsonFilePath);
									testNG.addTestParameter("dataflag", dataflag);
									testNG.addTestParameter("iteration", String.valueOf(dsId));
									testNG.addTestParameter("suitename", testSuiteData.get("name"));
									testNG.addTestParameter("platform", platform);

									jsonRunnerHelper.addMethods(testNG, methods, classMethods);

								} // for loop for data sets in suites in data
									// files like signup.json,signin.json etc
							} // if for runmode in executions in suite files
								// like signup.json,signin.json etc
						} // for loop for executions in suite file ends
					} // for loop for test cases ends
				} // if suite is not empty
			} // for loop for test suites ends
		} // for loop for platforms in testconfig ends

		testNG.run();

	}
}
