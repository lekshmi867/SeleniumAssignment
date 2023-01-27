package com.lekshmi.selenium.week16Assignment;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DownloadFile {

	WebDriver driver;

	@BeforeMethod
	public void setup() {

		HashMap<String, Object> chromePreferences = new HashMap<String, Object>();

		chromePreferences.put("download.default_directory", System.getProperty("user.dir"));

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePreferences);

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);

		driver.get("https://demoqa.com/upload-download");

	}

	@Test
	public void verifyUserIsAbleTODownloadFile() {
		WebElement downloadButton = driver.findElement(By.cssSelector("#downloadButton"));

		downloadButton.click();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File folder = new File(System.getProperty("user.dir"));

		File[] listOfFiles = folder.listFiles();
		File downloadedFile = null;
		boolean isFilePresent = false;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (file.getName().equals("sampleFile.jpeg")) {
					isFilePresent = true;
					downloadedFile = new File(file.getName());
				}
			}
		}

		Assert.assertTrue(isFilePresent, "File Not Found");

		downloadedFile.deleteOnExit();

	}

	@AfterMethod
	public void teadrDown() {
		driver.quit();
	}

}