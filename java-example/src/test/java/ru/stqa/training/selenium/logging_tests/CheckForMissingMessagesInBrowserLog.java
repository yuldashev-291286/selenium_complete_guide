package ru.stqa.training.selenium.logging_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckForMissingMessagesInBrowserLog {

  public static class MyListener extends AbstractWebDriverEventListener {
    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
      //System.out.println(by);
    }
    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
      //System.out.println(by +  " found");
    }
    @Override
    public void onException(Throwable throwable, WebDriver driver) {
      //System.out.println(throwable);
      File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      //Files.copy(tempFile, new File("screen.png"));
    }
  }

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    LoggingPreferences logPrefs = new LoggingPreferences();

    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
    capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

    driver = new EventFiringWebDriver(new ChromeDriver(capabilities));
    ((EventFiringWebDriver) driver).register(new MyListener());

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);

    driver.navigate().to("http://localhost/litecart/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }

  @Test
  public void testCheckForMissingMessagesInBrowserLog() {
    driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    List<WebElement> firstCategoryProducts = driver.findElements(By
            .cssSelector("a[href^='http://localhost/litecart/admin/" +
                    "?app=catalog&doc=edit_product&category_id=1&product_id=']"));
    for (int i = 0; i < (firstCategoryProducts.size()/2); i++) {
      driver.findElement(By
              .xpath("//a[@href='http://localhost/litecart/admin/?app=catalog&doc=edit_product" +
                      "&category_id=1&product_id=" + (i+1) + "'and @title='Edit']")).click();
      for (LogEntry logEntry : driver.manage().logs().get("performance").getAll()) {
        System.out.println(logEntry);
        assertNotNull(logEntry);
      }
      driver.navigate().back();
    }
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
