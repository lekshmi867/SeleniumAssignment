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

public class PerformMouseHover {
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

	@Test
	public void verifyWindowHandle() {
		String text = wd.findElement(By.cssSelector("h3.post-title.entry-title")).getText();
		sf.assertEquals(text,"How to perform mouse hover in Selenium Webdriver", "Incorrect text");
		String parentWindowHandle = wd.getWindowHandle();
		mouseHoverClick();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("div.dropdown-content a:first-of-type"))));
		WebElement firstTab = wd.findElement(By.cssSelector("div.dropdown-content a:first-of-type"));
		String secondWindowHandle = getWindowHandle(firstTab, parentWindowHandle);
		wd.switchTo().window(secondWindowHandle);
		sf.assertEquals(wd.getTitle(), "Selenium Webdriver Tutorial - Selenium Tutorial for Beginners", "Page is not valid");
		wd.switchTo().window(parentWindowHandle);

		mouseHoverClick();
		WebElement secondtTab = wd.findElement(By.cssSelector("div.dropdown-content a:nth-of-type(2)"));
		secondtTab.click();
		String secondTabTitle = "TestNG Tutorials for Selenium Webdriver with Real Time Examples";
		verifyTitle(secondTabTitle);

		mouseHoverClick();
		WebElement thirdtTab = wd.findElement(By.cssSelector("div.dropdown-content a:nth-of-type(3)"));
		thirdtTab.click();
		String thirdTabTitle = "Complete Ultimate Appium tutorial for beginners using JAVA for Selenium";
		verifyTitle(thirdTabTitle);
		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.quit();
	}

	String getWindowHandle(WebElement tab, String parentWindowHandle) {
		tab.click();
		Set<String> allWindowHandles = wd.getWindowHandles();
		allWindowHandles.stream().filter(p->!p.equals(parentWindowHandle)).forEach(h->childHandle=h);
		wd.switchTo().window(parentWindowHandle);
		return childHandle;
	}
	
	void mouseHoverClick() {
		WebElement mouseHoverBtn = wd.findElement(By.cssSelector("button.dropbtn"));
		action.moveToElement(mouseHoverBtn).perform();
	}
	
	void verifyTitle(String title) {
		sf.assertEquals(wd.getTitle(), title, "Page is not valid");
		wd.navigate().to("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
	}

}
