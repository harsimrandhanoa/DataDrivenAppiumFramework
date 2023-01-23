package com.appium.ddf.testcases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.appium.ddf.base.BaseTest;

public class Category extends BaseTest {

	@Test
	public void findCategory(ITestContext context) {
		app.log("Starting test :- findCategory");
		JSONObject data = (JSONObject) context.getAttribute("data");
		app.log("Data is" + String.valueOf(data));
		app.threadWait(5);
		app.click("accept_id");
		app.click("menuIcon_xpath");
		app.threadWait(2);
		app.scrollDown("once");
		app.threadWait(2);
		app.click("allCategory_xpath");
		app.threadWait(2);
		app.click("firstDropDown_xpath");
		app.threadWait(2);
		app.click("secondDropDown_xpath");
		app.threadWait(2);
		app.click("item_xpath");
	}

	@Test
	public void searchCategory(ITestContext context) {

		app.log("Starting test :- searchCategory");
		JSONObject data = (JSONObject) context.getAttribute("data");
		app.log("Data is " + String.valueOf(data));
		String categoryName = (String) data.get("categoryName");
		app.threadWait(5);
		app.click("accept_id");
		app.threadWait(2);
		app.type("searchBar_xpath", categoryName);
		app.threadWait(2);
		app.click("searchIcon_xpath");
		app.threadWait(2);
		app.click("filterTab_xpath");
		app.threadWait(2);
		app.click("priceDropDown_xpath");
		app.threadWait(2);
		app.click("price_xpath");

	}

}
