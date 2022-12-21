package com.lekshmi.selenium.week16Assignment;

import java.time.Duration;

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

public class ValidatePurchaseOfItem {
	WebDriver wd;
	WebDriverWait wdwait;
	SoftAssert sf = new SoftAssert();
	Actions action;

	@BeforeMethod
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "C:\\chrome driver 108\\chromedriver.exe");
		// intialise webdriver instance
		wd = new ChromeDriver();
		
		wdwait = new WebDriverWait(wd, Duration.ofSeconds(30));

		action = new Actions(wd);
		// Launch a page
		wd.get("https://www.demoblaze.com/index.html");
	}

	@Test
	public void verifyWindowHandle() {
		sf.assertEquals(wd.getTitle(),"STORE", "Invalid page title");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("div#tbodyid div:nth-of-type(3) div.card.h-100 div.card-block a"))));
		wd.findElement(By.cssSelector("div#tbodyid div:nth-of-type(3) div.card.h-100 div.card-block a")).click();
		
		String productFieldlocator ="h2.name";
		validateText(productFieldlocator, "Nexus 6");
		
		String productPriceFieldLocator = "h3.price-container";
		validateText(productPriceFieldLocator, "$650 *includes tax");
		
		wd.findElement(By.cssSelector("a.btn.btn-success.btn-lg")).click();
		wdwait.until(ExpectedConditions.alertIsPresent());
		wd.switchTo().alert().accept();
		wd.findElement(By.cssSelector("a#cartur")).click();
		
		String productTitleLocator = "tr.success td:nth-of-type(2)";
		validateText(productTitleLocator, "Nexus 6");
		
		String productPriceLocator = "tr.success td:nth-of-type(3)";
		validateText(productPriceLocator,"650");
		
		String productTotalPriceLocator = "h3#totalp";
		validateText(productTotalPriceLocator, "650");
		wd.findElement(By.cssSelector("button.btn.btn-success")).click();
		billingDetails();
		
		
		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.quit();
	}
	
	void validateText(String locator,String textToBeVerified) {
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector((locator))));
		WebElement element = wd.findElement(By.cssSelector(locator));
		String textFromWebElement= element.getText();
		sf.assertEquals(textFromWebElement,textToBeVerified, "Incorrect text");	
	}
	
	void billingDetails() {
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#name"))));
		WebElement nameField = wd.findElement(By.cssSelector("input#name"));	
		nameField.sendKeys("lekshmi");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#country"))));
		WebElement countryField = wd.findElement(By.cssSelector("input#country"));	
		countryField.sendKeys("canada");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#city"))));
		WebElement cityField = wd.findElement(By.cssSelector("input#city"));	
		cityField.sendKeys("brampton");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#card"))));
		WebElement cardField = wd.findElement(By.cssSelector("input#card"));	
		cardField.sendKeys("123456789");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#month"))));
		WebElement monthField = wd.findElement(By.cssSelector("input#month"));	
		monthField.sendKeys("march");
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("input#year"))));
		WebElement yearField = wd.findElement(By.cssSelector("input#year"));	
		yearField.sendKeys("2023");
		wd.findElement(By.cssSelector("button[onclick='purchaseOrder()']")).click();
		
		String thankyouLocator = "div.sweet-alert.showSweetAlert.visible h2";
		String thankyouTextt = "Thank you for your purchase!";
		validateText(thankyouLocator, thankyouTextt);
		
		wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(("div.sweet-alert.showSweetAlert.visible p"))));
		WebElement element = wd.findElement(By.cssSelector("div.sweet-alert.showSweetAlert.visible p"));
		String textFromWebElement= element.getText();
		verifyFinalReceipt(textFromWebElement);
		
	}
	
	void verifyFinalReceipt(String textFromWebElement) {
		boolean isAmountValid = textFromWebElement.contains("Amount: 650 USD");
		boolean isCardNumberValid = textFromWebElement.contains("Card Number: 123456789");
		boolean isNameValid = textFromWebElement.contains("Name: lekshmi");
		sf.assertEquals(isAmountValid, true);
		sf.assertEquals(isCardNumberValid, true);
		sf.assertEquals(isNameValid, true);
		wd.findElement(By.cssSelector("button.confirm.btn.btn-lg.btn-primary")).click();
		sf.assertEquals(wd.getCurrentUrl(),"https://www.demoblaze.com/index.html", "Invalid url");
	}
}
