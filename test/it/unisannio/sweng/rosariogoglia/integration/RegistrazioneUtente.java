package it.unisannio.sweng.rosariogoglia.integration;

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
		System.setProperty("webdriver.gecko.driver", "C:/Users/Rosario/git/AsteOnLine/geckodriver-v0.21.0-win64/geckodriver.exe");
	    driver = new FirefoxDriver();
	    baseUrl = "http://localhost:30000/AsteOnLine/index";
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testRegistrazioneUtenteVenditore() throws Exception {
	    driver.get("http://localhost:30000/AsteOnLine/index");
	    driver.findElement(By.linkText("Registrati")).click();
	    driver.findElement(By.id("campoTesto")).click();
	    driver.findElement(By.id("campoTesto")).clear();
	    driver.findElement(By.id("campoTesto")).sendKeys("Angela");
	    driver.findElement(By.name("cognome")).click();
	    driver.findElement(By.name("cognome")).clear();
	    driver.findElement(By.name("cognome")).sendKeys("Cerulo");
	    driver.findElement(By.name("codFis")).click();
	    driver.findElement(By.name("codFis")).sendKeys(Keys.ENTER);
	    driver.findElement(By.name("cognome")).clear();
	    driver.findElement(By.name("cognome")).sendKeys("Cerulo");
	    driver.findElement(By.name("indirizzo")).click();
	    driver.findElement(By.name("indirizzo")).clear();
	    driver.findElement(By.name("indirizzo")).sendKeys("via case sparse");
	    driver.findElement(By.id("provincia")).click();
	    new Select(driver.findElement(By.id("provincia"))).selectByVisibleText("Benevento");
	    driver.findElement(By.xpath("//option[@value='62']")).click();
	    driver.findElement(By.id("comune")).click();
	    driver.findElement(By.id("comune")).click();
	    new Select(driver.findElement(By.id("comune"))).selectByVisibleText("Vitulano");
	    driver.findElement(By.xpath("//option[@value='5227']")).click();
	    driver.findElement(By.name("cap")).click();
	    driver.findElement(By.name("cap")).clear();
	    driver.findElement(By.name("cap")).sendKeys("82038");
	    driver.findElement(By.name("telefono")).click();
	    driver.findElement(By.name("telefono")).clear();
	    driver.findElement(By.name("telefono")).sendKeys("3272356963");
	    driver.findElement(By.name("mail")).click();
	    driver.findElement(By.name("mail")).clear();
	    driver.findElement(By.name("mail")).sendKeys("angelacerulo@gmail.com");
	    driver.findElement(By.name("cc")).click();
	    driver.findElement(By.name("cc")).clear();
	    driver.findElement(By.name("cc")).sendKeys("5234171055368084");
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).click();
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[9]")).sendKeys(Keys.ENTER);
	    driver.findElement(By.name("cc")).clear();
	    driver.findElement(By.name("cc")).sendKeys("5234171055368084");
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).clear();
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[10]")).sendKeys("angela92");
	    driver.findElement(By.xpath("//form[@action='ServletRegistrazione']")).click();
	    driver.findElement(By.name("password2")).click();
	    driver.findElement(By.name("password2")).clear();
	    driver.findElement(By.name("password2")).sendKeys("angela92");
	    driver.findElement(By.name("tipCliente")).click();
	    driver.findElement(By.id("bottone")).click();
	    driver.findElement(By.id("campoTesto")).click();
	    driver.findElement(By.id("campoTesto")).clear();
	    driver.findElement(By.id("campoTesto")).sendKeys("angelaC");
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='campoTesto'])[2]")).sendKeys("angela92");
	    driver.findElement(By.id("bottone")).click();
	  }

	  @After
	  public void tearDown() throws Exception {
		Thread.sleep(10000);
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
