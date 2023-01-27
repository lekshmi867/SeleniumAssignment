package com.lekshmi.selenium.week16Assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UploadFile {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		driver.get("https://demoqa.com/upload-download");
		
	}
	
	
	@Test
	public void verifyuploadFunctionality() {
		WebElement uploadButton = driver.findElement(By.cssSelector("#uploadFile"));
		uploadFile("C:\\Users\\leksh\\Downloads\\Selenium Interview Questions .pdf", uploadButton);
	}
	
	@AfterMethod
	public void tearDown() {
		// closing the browser
		driver.quit();
	}
	
	public void uploadFile(String filePath, WebElement uploadButton) {
		uploadButton.sendKeys(filePath);
	}

}