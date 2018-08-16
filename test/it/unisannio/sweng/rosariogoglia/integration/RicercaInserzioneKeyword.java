package it.unisannio.sweng.rosariogoglia.integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RicercaInserzioneKeyword {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  System.setProperty("webdriver.gecko.driver", "C:/Users/Rosario/git/AsteOnLine/geckodriver-v0.21.0-win64/geckodriver.exe");
		
	  driver = new FirefoxDriver();
	  baseUrl = "http://localhost:30000/AsteOnLine/index";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

  }

  @Test
  public void testRicercaInserzioneKeyword() throws Exception {
    driver.get("http://localhost:30000/AsteOnLine/index");
    driver.findElement(By.xpath("//a[@id='logo']")).click();
    driver.findElement(By.id("autoKeyword")).click();
    driver.findElement(By.id("autoKeyword")).clear();
    driver.findElement(By.id("autoKeyword")).sendKeys("Calcio");
    driver.findElement(By.id("Calcio")).click();
    driver.findElement(By.id("bottone-Cerca")).click();
    driver.findElement(By.xpath("//img[@alt='Dettagli']")).click();
    driver.findElement(By.xpath("//div[2]/div/div[2]/div")).click();
    driver.findElement(By.xpath("//div[2]/div/div[2]/div")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
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
