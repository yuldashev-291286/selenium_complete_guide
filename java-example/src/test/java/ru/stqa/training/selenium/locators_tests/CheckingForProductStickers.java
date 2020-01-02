package ru.stqa.training.selenium.locators_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckingForProductStickers {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);
    driver.navigate().to("http://localhost/litecart/en/");
    wait.until(titleIs("Online Store | My Store"));
  }

  @Test
  public void testCheckingForProductStickers() {
    List<WebElement> productsFromMainPage = driver.findElements(By.cssSelector(".product"));
    List<WebElement> stickers = new ArrayList<WebElement>();
    for (WebElement product : productsFromMainPage) {
      stickers = product.findElements(By.cssSelector(".sticker"));
      assertEquals(1, stickers.size());
    }

  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
