package it.unisannio.sweng.rosariogoglia.integration;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class InserisciRimuoviProdotto {
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
  public void testInserisciProdotto() throws Exception {
	  driver.get("http://localhost:30000/AsteOnLine/ServletLogout");
	    driver.findElement(By.name("nick")).click();
	    driver.findElement(By.xpath("//a[@id='logo']")).click();
	    driver.findElement(By.name("nick")).click();
	    driver.findElement(By.name("nick")).clear();
	    driver.findElement(By.name("nick")).sendKeys("ros7");
	    driver.findElement(By.name("password")).click();
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys("rosario");
	    driver.findElement(By.id("bottone-Accedi")).click();
	    driver.findElement(By.xpath("//a[4]/strong")).click();
	    driver.findElement(By.id("categoria")).click();
	    new Select(driver.findElement(By.id("categoria"))).selectByVisibleText("Cellulari");
	    driver.findElement(By.xpath("//option[@value='3']")).click();
	    driver.findElement(By.id("produttore")).click();
	    new Select(driver.findElement(By.id("produttore"))).selectByVisibleText("Huawei");
	    driver.findElement(By.xpath("(//option[@value='7'])[2]")).click();
	    driver.findElement(By.id("campoTesto")).click();
	    driver.findElement(By.id("campoTesto")).clear();
	    driver.findElement(By.id("campoTesto")).sendKeys("Test Prodotto");
	    driver.findElement(By.id("bottone")).click();
	    driver.findElement(By.id("categoria2")).click();
	    new Select(driver.findElement(By.id("categoria2"))).selectByVisibleText("Cellulari");
	    driver.findElement(By.xpath("(//option[@value='3'])[2]")).click();
	    driver.findElement(By.id("produttore2")).click();
	    new Select(driver.findElement(By.id("produttore2"))).selectByVisibleText("Huawei");
	    driver.findElement(By.xpath("(//option[@value='7'])[3]")).click();
	    driver.findElement(By.id("prodotto2")).click();
	    new Select(driver.findElement(By.id("prodotto2"))).selectByVisibleText("Test Prodotto");
	    //driver.findElement(By.xpath("//option[@value='56']")).click();
	    driver.findElement(By.xpath("(//input[@id='bottone'])[3]")).click();
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
