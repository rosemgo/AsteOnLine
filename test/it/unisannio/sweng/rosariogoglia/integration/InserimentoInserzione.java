package it.unisannio.sweng.rosariogoglia.integration;
		
	import java.util.regex.Pattern;
	import java.util.concurrent.TimeUnit;
	import org.junit.*;
	import static org.junit.Assert.*;
	import static org.hamcrest.CoreMatchers.*;
	import org.openqa.selenium.*;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.support.ui.Select;

	public class InserimentoInserzione {
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
	  public void testInserzione() throws Exception {
		    driver.get("http://localhost:30000/AsteOnLine/index");
		    driver.findElement(By.name("nick")).click();
		    driver.findElement(By.name("nick")).clear();
		    driver.findElement(By.name("nick")).sendKeys("francescos");
		    driver.findElement(By.name("password")).click();
		    driver.findElement(By.name("password")).clear();
		    driver.findElement(By.name("password")).sendKeys("francesco");
		    driver.findElement(By.id("bottone-Accedi")).click();
		    driver.findElement(By.xpath("//a[2]/strong")).click();
		    driver.findElement(By.id("bottone1")).click();
		    driver.findElement(By.id("categoria")).click();
		    new Select(driver.findElement(By.id("categoria"))).selectByVisibleText("Cellulari");
		    driver.findElement(By.xpath("//option[@value='3']")).click();
		    driver.findElement(By.id("produttore")).click();
		    new Select(driver.findElement(By.id("produttore"))).selectByVisibleText("Huawei");
		    driver.findElement(By.xpath("(//option[@value='7'])[2]")).click();
		    driver.findElement(By.id("prodotto")).click();
		    new Select(driver.findElement(By.id("prodotto"))).selectByVisibleText("Huawei P10");
		    driver.findElement(By.xpath("//option[@value='46']")).click();
		    driver.findElement(By.id("campoTesto")).click();
		    driver.findElement(By.id("campoTesto")).clear();
		    driver.findElement(By.id("campoTesto")).sendKeys("Test Inserzione");
		    driver.findElement(By.name("descrizione")).click();
		    driver.findElement(By.name("descrizione")).clear();
		    driver.findElement(By.name("descrizione")).sendKeys("Test descrizione");
		    driver.findElement(By.name("prezzoIniziale")).click();
		    driver.findElement(By.name("prezzoIniziale")).clear();
		    driver.findElement(By.name("prezzoIniziale")).sendKeys("100");
		    driver.findElement(By.xpath("//p[12]")).click();
		    driver.findElement(By.name("data_scadenza")).click();
		    driver.findElement(By.name("data_scadenza")).clear();
		    driver.findElement(By.name("data_scadenza")).sendKeys("03/16/2021");
		 
		 // driver.findElement(By.name("data_scadenza")).click();
		 // driver.findElement(By.xpath("//td[@onclick='f_tcalUpdate(1533031200000)']")).click();
		 
		 /*
		    driver.findElement(By.name("fileUpload")).click();
		    driver.findElement(By.name("fileUpload")).clear();
		    driver.findElement(By.name("fileUpload")).sendKeys("C:\\fakepath\\P10 1.jpg");
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[4]")).click();
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[4]")).clear();
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[4]")).sendKeys("C:\\fakepath\\P10 2.jpg");
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[5]")).click();
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[5]")).clear();
		    driver.findElement(By.xpath("(//input[@id='campoTesto'])[5]")).sendKeys("C:\\fakepath\\P10 3.jpg");
		 */
		   
		    driver.findElement(By.id("bottone")).click();		    
		 // driver.findElement(By.xpath("//img[@alt='Dettagli']")).click();
		    
		    driver.findElement(By.xpath("//a[6]/strong")).click();
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
	


