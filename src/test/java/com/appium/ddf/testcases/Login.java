package com.appium.ddf.testcases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.appium.ddf.base.BaseTest;

public class Login extends BaseTest {

	@Test
	public void login(ITestContext context) {
		app.log("Starting test :- Login");

		JSONObject data = (JSONObject) context.getAttribute("data");
		app.log("Data is " + String.valueOf(data));
		String username = (String) data.get("email");
		String password = (String) data.get("password");

		app.threadWait(2);
		app.click("accept_id");
		app.click("signin_xpath");

		app.type("loginEmail_xpath", username);
		app.type("loginPassword_xpath", password);
		app.click("loginButton_xpath");
	}
}
