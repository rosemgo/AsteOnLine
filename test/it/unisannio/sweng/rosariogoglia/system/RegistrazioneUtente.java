package it.unisannio.sweng.rosariogoglia.system;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RegistrazioneUtente {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver", "../AsteOnLine/geckodriver-v0.21.0-win64/geckodriver.exe");
		
	driver = new FirefoxDriver();
    baseUrl = "http://localhost:30000/AsteOnLine/index";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testRegistrazioneUtente() throws Exception {
    driver.get("http://localhost:30000/AsteOnLine/index");
    driver.findElement(By.linkText("Registrati")).click();
    driver.findElement(By.id("campoTesto")).click();
    driver.findElement(By.id("campoTesto")).clear();
    driver.findElement(By.id("campoTesto")).sendKeys("Angelo");
    driver.findElement(By.name("cognome")).click();
    driver.findElement(By.name("cognome")).clear();
    driver.findElement(By.name("cognome")).sendKeys("Goglia");
    driver.findElement(By.name("codFis")).click();
    driver.findElement(By.name("codFis")).clear();
    driver.findElement(By.name("codFis")).sendKeys("GGLNGL58E31A765F");
    driver.findElement(By.name("indirizzo")).click();
    driver.findElement(By.name("indirizzo")).clear();
    driver.findElement(By.name("indirizzo")).sendKeys("via della sarta santa");
    driver.findElement(By.id("provincia")).click();
    new Select(driver.findElement(By.id("provincia"))).selectByVisibleText("Benevento");
    driver.findElement(By.xpath("//option[@value='62']")).click();
    driver.findElement(By.id("comune")).click();
    new Select(driver.findElement(By.id("comune"))).selectByVisibleText("Vitulano");
    driver.findElement(By.xpath("//option[@value='5227']")).click();
    driver.findElement(By.name("cap")).click();
    driver.findElement(By.name("cap")).clear();
    driver.findElement(By.name("cap")).sendKeys("82038");
    driver.findElement(By.name("telefono")).click();
    driver.findElement(By.name("telefono")).clear();
    driver.findElement(By.name("telefono")).sendKeys("3385049575");
    driver.findElement(By.name("mail")).click();
    driver.findElement(By.name("mail")).clear();
    driver.findElement(By.name("mail")).sendKeys("angelo.g@gmail.com");
    driver.findElement(By.name("cc")).click();
    driver.findElement(By.name("cc")).clear();
    driver.findElement(By.name("cc")).sendKeys("4023600921272987");
//    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).click();
//    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).clear();
//    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).sendKeys("palpasc");
//    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).clear();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).sendKeys("angelog");
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).clear();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).sendKeys("angelo58");
    driver.findElement(By.name("password2")).click();
    driver.findElement(By.name("password2")).clear();
    driver.findElement(By.name("password2")).sendKeys("angelo58");
    driver.findElement(By.name("tipCliente")).click();
    driver.findElement(By.id("bottone")).click();
    driver.findElement(By.id("campoTesto")).click();
    driver.findElement(By.id("campoTesto")).clear();
    driver.findElement(By.id("campoTesto")).sendKeys("angelog");
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).sendKeys("angelo58");
    Thread.sleep(2000);
    driver.findElement(By.id("bottone")).click();
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
