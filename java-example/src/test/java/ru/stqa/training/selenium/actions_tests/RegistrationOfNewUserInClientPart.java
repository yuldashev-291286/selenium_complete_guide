package ru.stqa.training.selenium.actions_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class RegistrationOfNewUserInClientPart {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new FirefoxDriver();
//    driver = new ChromeDriver();
//    driver = new InternetExplorerDriver();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);

    driver.navigate().to("http://localhost/litecart/en/");
    wait.until(titleIs("Online Store | My Store"));
  }

  @Test
  public void testRegistrationOfNewUserInClientPart() {
    driver.findElement(By.linkText("New customers click here")).click();
    wait.until(titleIs("Create Account | My Store"));
    driver.findElement(By.name("firstname")).sendKeys("Ivanov");
    driver.findElement(By.name("lastname")).sendKeys("Ivan");
    driver.findElement(By.name("address1")).sendKeys("USA");
    driver.findElement(By.name("postcode")).sendKeys("36101-3619");
    driver.findElement(By.name("city")).sendKeys("Washington");

    JavascriptExecutor js = (JavascriptExecutor) driver;
    Select selectCountry = new Select(driver.findElement(By.name("country_code")));
    js.executeScript("arguments[0].selectedIndex = 224; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectCountry);

    String email = emailGenerator();
    String password = "123456";
    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("phone")).sendKeys("+1 800 469-92-69");
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("confirmed_password")).sendKeys(password);
    driver.findElement(By.name("create_account")).click();

    Select selectState = new Select(driver.findElement(By.name("zone_code")));
    js.executeScript("arguments[0].selectedIndex = 1; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectState);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("confirmed_password")).sendKeys(password);

    driver.findElement(By.name("create_account")).click();
    wait.until(titleIs("Online Store | My Store"));
    driver.findElement(By.linkText("Logout")).click();

    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("Online Store | My Store"));
    driver.findElement(By.linkText("Logout")).click();
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  public String emailGenerator() {
    long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost", now);
    return email;
  }

}
