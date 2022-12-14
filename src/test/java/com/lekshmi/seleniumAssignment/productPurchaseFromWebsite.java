package com.lekshmi.seleniumAssignment;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.Test;

public class productPurchaseFromWebsite {
	WebDriver wd;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\chrome driver 108\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		wd.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
	}

	@Test
	public void verifyProductPurchase() {
		String email = userRegistration();
		wd.findElement(By.cssSelector("div#common-success>div>aside>div.list-group a:last-of-type")).click();
		sleep();
		//navigating to the login page
		wd.navigate().to("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		WebElement emailInputField = wd.findElement(By.cssSelector("input#input-email"));
		WebElement passwordInputField = wd.findElement(By.cssSelector("input#input-password"));
		WebElement loginBtn = wd.findElement(By.cssSelector("input[type='submit']"));
		//method to verify login
		verifyUserLogin(emailInputField, passwordInputField, loginBtn, email);

		WebElement phonesPDAField = wd
				.findElement(By.cssSelector("nav.navbar div:nth-of-type(2)>ul li:nth-of-type(6) a"));
		//method to select the phoneTab
		selectPhonesTabFromNavBar(phonesPDAField);

		WebElement selectedPhoneField = wd.findElement(By.cssSelector(
				"div#product-category div:last-of-type div#content>div:nth-of-type(2) div:nth-of-type(3)>div.product-thumb div.caption a"));
		sleep();
		//method to select the phone
		selecedtPhone(selectedPhoneField);

		WebElement itemInCartBtn = wd.findElement(By.cssSelector("div#cart>button"));
		//method to add item to cart
		verifyItemInCartAndCheckout(itemInCartBtn);
		//method to verify billing details
		verifyBillingDetails();

	}

	@AfterMethod
	public void tearDown() {
		// closing the browser
		wd.close();
	}

	public void verifyUserLogin(WebElement emailInputField, WebElement passwordInputField, WebElement loginBtn,
			String email) {
		emailInputField.sendKeys(email);
		passwordInputField.sendKeys("password123");
		loginBtn.submit();
		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "My Account", "User is not signed in");

	}

	public void selectPhonesTabFromNavBar(WebElement phonesPDAField) {
		phonesPDAField.click();
		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "Phones & PDAs", "Page is not valid");
	}

	public void selecedtPhone(WebElement selectedPhoneField) {
		selectedPhoneField.click();
		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "Palm Treo Pro", "Page is not valid");

		WebElement addtoCartBtn = wd.findElement(By.cssSelector("button#button-cart"));
		addtoCartBtn.click();
	}

	public void verifyItemInCartAndCheckout(WebElement itemInCartBtn) {
		itemInCartBtn.click();
		sleep();
		WebElement textElement = wd.findElement(By.cssSelector("span#cart-total"));
		String text = textElement.getText();
		Assert.assertEquals(text, "1 item(s) - $337.99", "No match for item");

		WebElement checkoutField = wd
				.findElement(By.cssSelector("ul.dropdown-menu.pull-right li:last-of-type>div>p>a:last-of-type strong"));
		checkoutField.click();

	}

	public void verifyBillingDetails() {
		WebElement firstNameInputField = wd.findElement(By.cssSelector("input#input-payment-firstname"));
		firstNameInputField.sendKeys("lekshmi");
		WebElement lastNameInputField = wd.findElement(By.cssSelector("input#input-payment-lastname"));
		lastNameInputField.sendKeys("vijayan");
		WebElement companyInputField = wd.findElement(By.cssSelector("input#input-payment-company"));
		companyInputField.sendKeys("abc");
		WebElement addressInputField = wd.findElement(By.cssSelector("input#input-payment-address-1"));
		addressInputField.sendKeys("Apt 101 xyz street");
		WebElement cityInputField = wd.findElement(By.cssSelector("input#input-payment-city"));
		cityInputField.sendKeys("brampton");
		WebElement postalCodeInputField = wd.findElement(By.cssSelector("input#input-payment-postcode"));
		postalCodeInputField.sendKeys("lgf1b5");
		selectByVisibleText(wd.findElement(By.cssSelector("select#input-payment-country")), "Canada");
		selectByVisibleText(wd.findElement(By.cssSelector("select#input-payment-zone")), "Nunavut");
		wd.findElement(By.cssSelector("input#button-payment-address")).click();

		Assert.assertTrue(
				wd.findElement(By.cssSelector("form.form-horizontal>div:first-of-type input[name='shipping_address']"))
						.isSelected(),
				"Element not selected");
		sleep();
		wd.findElement(By.cssSelector("input#button-shipping-address")).click();

		Assert.assertTrue(wd.findElement(By.cssSelector("input[name='shipping_method']")).isSelected(),
				"Element not selected");
		wd.findElement(By.cssSelector("input#button-shipping-method")).click();

		Assert.assertTrue(wd.findElement(By.cssSelector("input[name='payment_method']")).isSelected(),
				"Element not selected");
		sleep();
		wd.findElement(By.cssSelector("input[name='agree']")).click();
		wd.findElement(By.cssSelector("input#button-payment-method")).click();
		WebElement productField = wd.findElement(By.cssSelector("div.table-responsive table tbody tr td a"));
		sleep();
		String productText = productField.getText();
		Assert.assertEquals(productText, "Palm Treo Pro");
		WebElement noOfItemField = wd
				.findElement(By.cssSelector("div.table-responsive table tbody tr td:nth-of-type(3)"));
		sleep();
		String noOfItemText = noOfItemField.getText();
		Assert.assertEquals(noOfItemText, "1");

		wd.findElement(By.cssSelector("input#button-confirm")).click();
		sleep();
		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "Your order has been placed!", "order not placed");

	}

	
	public void selectByVisibleText(WebElement element, String text) {
		Select sc = new Select(element);
		sc.selectByVisibleText(text);
	}

	public String userRegistration() {
		WebElement firstNameInputField = wd.findElement(By.cssSelector("input[name='firstname']"));
		WebElement lastNameInputField = wd.findElement(By.cssSelector("input[name='lastname']"));
		WebElement emailInputField = wd.findElement(By.cssSelector("input[name='email']"));
		WebElement telephoneNameInputField = wd.findElement(By.cssSelector("input[name='telephone']"));
		WebElement passwordInputField = wd.findElement(By.cssSelector("input[name='password']"));
		WebElement passwordConfirmInputField = wd.findElement(By.cssSelector("input[name='confirm']"));
		WebElement subscribeRadioBtn = wd.findElement(By.cssSelector("div.radio label:last-of-type>input"));
		WebElement privacyCheckBox = wd.findElement(By.cssSelector("input[name='agree']"));
		WebElement continueBtn = wd.findElement(By.cssSelector("input.btn.btn-primary"));

		String email = randomEmail();
		firstNameInputField.sendKeys("Lekshmi");
		lastNameInputField.sendKeys("Vijayan");
		emailInputField.sendKeys(email);
		telephoneNameInputField.sendKeys("9895808541");
		passwordInputField.sendKeys("password123");
		passwordConfirmInputField.sendKeys("password123");
		subscribeRadioBtn.isSelected();
		privacyCheckBox.click();
		continueBtn.submit();

		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "Your Account Has Been Created!", "Registration failed");
		return email;
	}

	public String randomEmail() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(999);
		String email = randomNumber + "@gmail.com";
		return email;
	}

	public void sleep() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
