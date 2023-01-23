package com.appium.ddf.testcases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.appium.ddf.base.BaseTest;

public class CreateUser extends BaseTest {

	@Test
	public void signUp(ITestContext context) {
		app.log("Starting test :- signUp");

		JSONObject data = (JSONObject) context.getAttribute("data");
		app.log("Data is" + String.valueOf(data));
		String username = (String) data.get("email");
		String password = (String) data.get("password");
		String city = (String) data.get("city");

		app.threadWait(2);
		app.click("accept_id");
		app.click("signin_xpath");
		app.click("signup_xpath");
		app.type("password_xpath", password);
		app.type("repassword_xpath", password);
		app.scrollDown("once");
		app.threadWait(5);
		app.click("city_xpath");
		app.threadWait(5);

		app.scrollIntoView(city);
		app.threadWait(2);
		app.clickOnText(city);
		app.threadWait(2);
		app.click("signupButton_xpath");

	}

}
