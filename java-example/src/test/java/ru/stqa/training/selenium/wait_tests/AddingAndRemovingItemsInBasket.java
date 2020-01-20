package ru.stqa.training.selenium.wait_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class AddingAndRemovingItemsInBasket {

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
  public void testAddingAndRemovingItemsInBasket() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (int i = 0; i < 3; i++) {
      List<WebElement> mostPopularProducts = driver.findElements(By.cssSelector("#box-most-popular li"));
      mostPopularProducts.get(0).click();

      int productsCounterBefore = Integer.parseInt(driver.findElement(By
              .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity")).getText());

      if (!isElementNotPresent(driver, By.cssSelector("select[name='options[Size]']"))) {
        Select selectOptions = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
        js.executeScript("arguments[0].selectedIndex = 1; " +
                "arguments[0].dispatchEvent(new Event('change'))", selectOptions);
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
      } else {
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
      }

      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      alert.accept();
      driver.navigate().refresh();

      wait.until(attributeContains(driver.findElement(By
                      .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity"))
              , "textContent", Integer.toString(productsCounterBefore + 1)));
      wait.until(textToBePresentInElement(driver.findElement(By
                      .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity"))
              , Integer.toString(productsCounterBefore + 1)));

      int productsÑounterAfter = Integer.parseInt(driver.findElement(By
              .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity")).getText());
      assertEquals("Ñ÷åò÷èê òîâàðîâ íå óâåëè÷èëñÿ.", productsCounterBefore + 1
              , productsÑounterAfter);

      driver.navigate().back();
    }

    WebElement checkout = driver.findElement(By
            .cssSelector(".link[href='http://localhost/litecart/en/checkout']"));
    js.executeScript("arguments[0].scrollIntoView();", checkout);
    checkout.click();

    List<WebElement> shortcuts = driver.findElements(By.cssSelector(".shortcuts li"));
    for(int i = 0; i < shortcuts.size(); i++) {
      if (i != shortcuts.size() - 1) {
        List<WebElement> dataTable = driver.findElements(By.cssSelector(".dataTable tr"));
        driver.findElement(By.cssSelector(".shortcuts li")).click();
        driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
        wait.until(numberOfElementsToBe(By.cssSelector(".dataTable tr"), dataTable.size() - 1));
      } else {
        driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".dataTable"))));
      }
    }

  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  boolean isElementNotPresent(WebDriver driver, By locator) {
    try {
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      return driver.findElements(locator).size() == 0;
    } finally {
      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); }
  }

  boolean isElementPresent(WebDriver driver, By locator) {
    try {
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      return driver.findElements(locator).size() > 0;
    } finally {
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); }
  }

}
