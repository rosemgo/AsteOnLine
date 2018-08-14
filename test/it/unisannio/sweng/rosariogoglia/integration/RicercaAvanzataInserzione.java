package it.unisannio.sweng.rosariogoglia.integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RicercaAvanzataInserzione {
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
  public void testRicercaAvanzataInserzione() throws Exception {
    driver.get("http://localhost:30000/AsteOnLine/ricercaAvanzata.jsp");
    driver.findElement(By.xpath("//a[@id='logo']")).click();
    driver.findElement(By.linkText("Ricerca avanzata")).click();
    driver.findElement(By.xpath("//form[@action='ServletRicercaAvanzataInserzione']")).click();
    driver.findElement(By.xpath("(//select[@name='categoria'])[2]")).click();
    new Select(driver.findElement(By.xpath("(//select[@name='categoria'])[2]"))).selectByVisibleText("Cellulari");
    driver.findElement(By.xpath("(//option[@value='3'])[2]")).click();
    driver.findElement(By.id("campoTesto3")).click();
    driver.findElement(By.id("campoTesto3")).clear();
    driver.findElement(By.id("campoTesto3")).sendKeys("10");
    driver.findElement(By.name("prezzoMax")).click();
    driver.findElement(By.name("prezzoMax")).clear();
    driver.findElement(By.name("prezzoMax")).sendKeys("1000");
    driver.findElement(By.xpath("//p[14]")).click();
    driver.findElement(By.xpath("(//input[@id='bottone-Cerca'])[3]")).click();
    driver.findElement(By.linkText("1")).click();
    Thread.sleep(2000);
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

