package com.lekshmi.selenium.week16Assignment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidateTitleOfAllTabs {
	WebDriver wd;
	WebDriverWait wdwait;
	SoftAssert sf = new SoftAssert();

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "C:\\chrome driver 108\\chromedriver.exe");

		wd = new ChromeDriver();

		wdwait = new WebDriverWait(wd, Duration.ofSeconds(10));

		wd.get("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
	}

	@Test
	public void verifyWindowHandle() {

		WebElement textElement = wd.findElement(By.cssSelector("h3.post-title.entry-title"));	
		sf.assertEquals(textElement.getText(), "Multiple window examples", "Text is not valid");
		Set<String> allWindowHandles1 = wd.getWindowHandles();
		
		
		WebElement firstTab = wd.findElement(By.cssSelector("div.post-body.entry-content a:first-of-type"));
		firstTab.click();
		Set<String> allWindowHandles2 = wd.getWindowHandles();
		String firstTabWindowHandle= windowHandle(allWindowHandles1,allWindowHandles2);
		
		
		WebElement secondTab = wd.findElement(By.cssSelector("div.post-body.entry-content a:nth-of-type(2)"));
		secondTab.click();
		Set<String> allWindowHandles3 = wd.getWindowHandles();
		String secondTabWindowHandle= windowHandle(allWindowHandles2,allWindowHandles3);

		
		
		WebElement thirdTab = wd.findElement(By.cssSelector("div.post-body.entry-content a:nth-of-type(3)"));
		thirdTab.click();
		Set<String> allWindowHandles4 = wd.getWindowHandles();
		String thirdTabWindowHandle= windowHandle(allWindowHandles3,allWindowHandles4);
		
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("div.post-body.entry-content a:nth-of-type(4)"))));
		WebElement fourthTab = wd.findElement(By.cssSelector("div.post-body.entry-content a:nth-of-type(4)"));
		fourthTab.click();
		String fourthTabWindowHandle = wd.getWindowHandle(); 
		
		
		verifyTitle(firstTabWindowHandle,"Google");
		verifyTitle(secondTabWindowHandle, "Facebook - log in or sign up");
		verifyTitle(thirdTabWindowHandle, "Yahoo | Mail, Weather, Search, News, Finance, Sports, Shopping, Entertainment, Video");
		verifyTitle(fourthTabWindowHandle, "Facebook - log in or sign up");
		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.quit();
	}
	
	String windowHandle(Set<String> allWindowHandles1, Set<String> allWindowHandles2) {
		Set<String> diff=Sets.difference(allWindowHandles2, allWindowHandles1);
		List<String> list = new ArrayList<>(diff);
		return list.get(0);		
	}
		
	
	void verifyTitle(String handle, String title) {
		wd.switchTo().window(handle);
		sf.assertEquals(wd.getTitle(), title, "Page is not valid");
	}
}
