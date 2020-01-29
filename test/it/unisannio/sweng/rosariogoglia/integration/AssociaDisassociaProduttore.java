package it.unisannio.sweng.rosariogoglia.integration;



	import java.util.regex.Pattern;
	import java.util.concurrent.TimeUnit;
	import org.junit.*;
	import static org.junit.Assert.*;
	import static org.hamcrest.CoreMatchers.*;
	import org.openqa.selenium.*;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.support.ui.Select;

	
	
	public class AssociaDisassociaProduttore {
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
	  public void testAssociaDisassociaProduttore() throws Exception {
		  driver.get("http://localhost:30000/AsteOnLine/index");
		    driver.findElement(By.xpath("//a[@id='logo']")).click();
		    driver.findElement(By.name("nick")).click();
		    driver.findElement(By.name("nick")).clear();
		    driver.findElement(By.name("nick")).sendKeys("ros7");
		    driver.findElement(By.name("password")).click();
		    driver.findElement(By.name("password")).clear();
		    driver.findElement(By.name("password")).sendKeys("rosario");
		    driver.findElement(By.id("bottone-Accedi")).click();
		    driver.findElement(By.xpath("//a[3]/strong")).click();
		    driver.findElement(By.id("campoTesto")).click();
		    driver.findElement(By.id("campoTesto")).clear();
		    driver.findElement(By.id("campoTesto")).sendKeys("Test Produttore");
		    driver.findElement(By.name("website")).click();
		    driver.findElement(By.name("website")).clear();
		    driver.findElement(By.name("website")).sendKeys("www.test.com");
		    driver.findElement(By.id("bottone")).click();
		    driver.findElement(By.xpath("//a[2]/strong")).click();
		    driver.findElement(By.id("cate")).click();
		    new Select(driver.findElement(By.id("cate"))).selectByVisibleText("Prodotti per animali");
		    driver.findElement(By.xpath("(//option[@value='10'])[3]")).click();
		    driver.findElement(By.id("prod")).click();
		    new Select(driver.findElement(By.id("prod"))).selectByVisibleText("Test Produttore");
//		    driver.findElement(By.xpath("//option[@value='32']")).click();
		    driver.findElement(By.xpath("(//input[@id='bottone'])[7]")).click();
		    driver.findElement(By.id("categoria2")).click();
		    new Select(driver.findElement(By.id("categoria2"))).selectByVisibleText("Prodotti per animali");
		    driver.findElement(By.xpath("(//option[@value='10'])[4]")).click();
		    driver.findElement(By.id("produttore2")).click();
		    new Select(driver.findElement(By.id("produttore2"))).selectByVisibleText("Test Produttore");
//		    driver.findElement(By.xpath("//option[@value='32']")).click();
		    driver.findElement(By.xpath("(//input[@id='bottone'])[9]")).click();
		    driver.findElement(By.xpath("//a[3]/strong")).click();
		    driver.findElement(By.name("idProduttore")).click();
		    new Select(driver.findElement(By.name("idProduttore"))).selectByVisibleText("Test Produttore");
//		    driver.findElement(By.xpath("//option[@value='32']")).click();
		    driver.findElement(By.xpath("(//input[@id='bottone'])[3]")).click();
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



