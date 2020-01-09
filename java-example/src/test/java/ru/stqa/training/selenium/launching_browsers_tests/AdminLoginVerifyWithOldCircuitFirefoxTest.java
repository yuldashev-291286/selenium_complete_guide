package ru.stqa.training.selenium.launching_browsers_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AdminLoginVerifyWithOldCircuitFirefoxTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary(new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")));

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(FirefoxDriver.MARIONETTE, false);
    caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

    driver = new FirefoxDriver(caps);
    System.out.println(((HasCapabilities) driver).getCapabilities());

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void testAdminLoginVerifyWithOldCircuitFirefox() {
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
