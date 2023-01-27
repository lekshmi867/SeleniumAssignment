package com.lekshmi.selenium.week16Assignment;



import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class XpathInvocation {
	WebDriver wd;
	WebDriverWait wait;
	SoftAssert sf = new SoftAssert();
	Actions action;
	String childHandle = null;

	@BeforeMethod
	public void setUp() {

		// Basic Setup to begin with Selenium
		System.setProperty("webdriver.chrome.driver", "C:\\chrome driver 108\\chromedriver.exe");

		// intialise webdriver instance
		wd = new ChromeDriver();

		wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		action = new Actions(wd);
		// Launch a page
		wd.get("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
	}

	@Test(invocationCount=50)
	public void verifyWindowHandle() {
		WebElement mouseHoverBtn = wd.findElement(By.xpath("//button[text()='Automation Tools']"));
		action.moveToElement(mouseHoverBtn).perform();	
	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.quit();
	}


}

