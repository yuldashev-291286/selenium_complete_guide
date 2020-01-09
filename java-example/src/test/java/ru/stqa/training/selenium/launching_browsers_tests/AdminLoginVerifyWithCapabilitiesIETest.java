package ru.stqa.training.selenium.launching_browsers_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AdminLoginVerifyWithCapabilitiesIETest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("unexpectedAlertBehaviour", "dismiss");

    caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
    caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

    driver = new InternetExplorerDriver(caps);
    System.out.println(((HasCapabilities) driver).getCapabilities());

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void testAdminLoginVerifyWithCapabilitiesIE() {
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
