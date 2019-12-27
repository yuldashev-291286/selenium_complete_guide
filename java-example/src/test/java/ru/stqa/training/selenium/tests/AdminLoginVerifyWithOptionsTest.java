package ru.stqa.training.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AdminLoginVerifyWithOptionsTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-fullscreen");
    driver = new ChromeDriver(options);
    //DesiredCapabilities caps = new DesiredCapabilities();
    //caps.setCapability(ChromeOptions.CAPABILITY, options);
    //driver = new ChromeDriver(caps);
    //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void testAdminLoginVerifyWithOptions() {
    driver.navigate().to("http://localhost/litecart/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
