package it.unisannio.sweng.rosariogoglia.integration;



import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AssociaDisassociaKeyword {
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
  public void testAssociaparolaChiave() throws Exception {
    driver.get("http://localhost:30000/AsteOnLine/index");
    driver.findElement(By.name("nick")).click();
    driver.findElement(By.name("nick")).clear();
    driver.findElement(By.name("nick")).sendKeys("ros7");
    driver.findElement(By.name("password")).click();
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("rosario");
    driver.findElement(By.id("bottone-Accedi")).click();
    driver.findElement(By.xpath("//a[4]/strong")).click();
    driver.findElement(By.name("listaKeywords")).click();
    driver.findElement(By.name("listaKeywords")).click();
    driver.findElement(By.name("listaKeywords")).clear();
    driver.findElement(By.name("listaKeywords")).sendKeys("Test");
    driver.findElement(By.xpath("(//input[@id='bottone'])[7]")).click();
    driver.findElement(By.id("categoria4")).click();
    new Select(driver.findElement(By.id("categoria4"))).selectByVisibleText("Cellulari");
    driver.findElement(By.xpath("(//option[@value='3'])[6]")).click();
    driver.findElement(By.id("produttore4")).click();
    new Select(driver.findElement(By.id("produttore4"))).selectByVisibleText("Apple");
    driver.findElement(By.xpath("(//option[@value='5'])[7]")).click();
    driver.findElement(By.id("prodotto4")).click();
    new Select(driver.findElement(By.id("prodotto4"))).selectByVisibleText("iPhone 8");
    driver.findElement(By.xpath("(//option[@value='8'])[7]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[7]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[7]")).clear();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[7]")).sendKeys("Test");
    driver.findElement(By.xpath("(//input[@id='bottone'])[13]")).click();
    driver.findElement(By.id("categoria5")).click();
    new Select(driver.findElement(By.id("categoria5"))).selectByVisibleText("Cellulari");
    driver.findElement(By.xpath("(//option[@value='3'])[7]")).click();
    driver.findElement(By.id("produttore5")).click();
    new Select(driver.findElement(By.id("produttore5"))).selectByVisibleText("Apple");
    driver.findElement(By.xpath("(//option[@value='5'])[8]")).click();
    driver.findElement(By.id("prodotto5")).click();
    new Select(driver.findElement(By.id("prodotto5"))).selectByVisibleText("iPhone 8");
    driver.findElement(By.xpath("(//option[@value='8'])[8]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[8]")).click();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[8]")).clear();
    driver.findElement(By.xpath("(//input[@id='campoTesto'])[8]")).sendKeys("Test");
    driver.findElement(By.xpath("(//input[@id='bottone'])[15]")).click();
    driver.findElement(By.name("keyword")).click();
    driver.findElement(By.name("keyword")).clear();
    driver.findElement(By.name("keyword")).sendKeys("Test");
    driver.findElement(By.xpath("(//input[@id='bottone'])[9]")).click();
    driver.findElement(By.xpath("//a[7]/strong")).click();
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

