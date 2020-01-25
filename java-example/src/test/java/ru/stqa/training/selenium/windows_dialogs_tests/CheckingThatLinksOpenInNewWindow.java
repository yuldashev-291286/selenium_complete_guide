package ru.stqa.training.selenium.windows_dialogs_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckingThatLinksOpenInNewWindow {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);

    driver.navigate().to("http://localhost/litecart/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }

  @Test
  public void testCheckingThatLinksOpenInNewWindow() {
    driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    driver.findElement(By
            .cssSelector(".button[href='http://localhost/litecart/admin/?app=countries&doc=edit_country']"))
            .click();
    List<WebElement> linksToExternalPages = driver.findElements(By.cssSelector(".fa-external-link"));
    for (WebElement linkToNewWindow : linksToExternalPages) {
      // запоминаем идентификатор текущего окна
      String originalWindow = driver.getWindowHandle();
      // запоминаем идентификаторы уже открытых окон
      Set<String> existingWindows = driver.getWindowHandles();
      // кликаем кнопку, которая открывает новое окно
      linkToNewWindow.click();
      // ждем появления нового окна, с новым идентификатором
      String newWindow = wait.until(anyWindowOtherThan(existingWindows));
      // переключаемся в новое окно
      driver.switchTo().window(newWindow);
      // закрываем его
      driver.close();
      // и возвращаемся в исходное окно
      driver.switchTo().window(originalWindow);
    }

  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  //Ожидание появления нового окна
  public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
    return new ExpectedCondition<String>() {
      public String apply(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
      }
    };
  }

}
