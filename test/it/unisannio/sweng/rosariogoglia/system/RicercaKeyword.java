package it.unisannio.sweng.rosariogoglia.system;
	

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class RicercaKeyword{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	//String driverPath = "C:/Users/Rosario/Desktop/geckodriver-v0.21.0-win64/";  
	//System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver.exe");
	
	//driver per google chrome
//	System.setProperty("webdriver.chrome.driver","C:/Users/Rosario/Desktop/driverchrome/chromedriver.exe");
	//driver per mozilla firefox 
	System.setProperty("webdriver.gecko.driver", "../AsteOnLine/geckodriver-v0.21.0-win64/geckodriver.exe");
	
	
	driver = new FirefoxDriver();
	  
//	driver = new ChromeDriver();
	baseUrl = "http://localhost:30000/AsteOnLine/index";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  
  }

  @Test
  public void testPrimo() throws Exception {
	//driver.get("http://www.google.com");
	
    driver.get("http://localhost:30000/AsteOnLine/index");
    driver.findElement(By.xpath("//a[@id='logo']")).click();
    driver.findElement(By.id("autoKeyword")).click();
    driver.findElement(By.id("autoKeyword")).clear();
    driver.findElement(By.id("autoKeyword")).sendKeys("Cellulare");
    driver.findElement(By.id("bottone-Cerca")).click();
  
  }

  @After
  public void tearDown() throws Exception {
    Thread.sleep(10000); //inserisco la pausa in modo tale da non chiudere subito la finestra di test
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


	
	


