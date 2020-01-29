package it.unisannio.sweng.rosariogoglia.integration;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginLogout {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "../AsteOnLine/geckodriver-v0.21.0-win64/geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:30000/AsteOnLine/index";
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @Test
  public void testLoginLogout() throws Exception {
    driver.get("http://localhost:30000/AsteOnLine/index");
    Thread.sleep(2000);
    driver.findElement(By.xpath("//a[@id='logo']")).click();

  //  driver.findElement(By.linkText("Home")).click();
    driver.findElement(By.name("nick")).click();
    driver.findElement(By.name("nick")).clear();
    driver.findElement(By.name("nick")).sendKeys("steto");
    Thread.sleep(2000);
    driver.findElement(By.name("password")).click();
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("stefano1");
    Thread.sleep(2000);
    driver.findElement(By.id("bottone-Accedi")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//a[5]/strong")).click();
    Thread.sleep(1000);
  }

  @After
  public void tearDown() throws Exception {
	  Thread.sleep(1000);
	driver.quit();
	//driver.close();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

