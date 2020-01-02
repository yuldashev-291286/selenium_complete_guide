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

import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class PassingThroughAllSections {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);

    driver.navigate().to("http://localhost/litecart/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    wait.until(titleIs("My Store"));
  }

  @Test
  public void testPassingThroughAllSections() {
    List<WebElement> parentMenuItems = driver.findElements(By.id("app-"));
    List<String> nameParentMenuItems = new ArrayList<String>();
    for (WebElement Item : parentMenuItems) {
      nameParentMenuItems.add(Item.getText());
    }

    String header = null;
    for (String nameItem : nameParentMenuItems) {
      driver.findElement(By.linkText(nameItem)).click();
      header = driver.findElement(By.cssSelector("#content h1")).getText();
      assertNotNull(header);

      List<WebElement> childMenuItems = driver.findElements(By.cssSelector("li#app- .docs li"));
      List<String> nameChildMenuItems = new ArrayList<String>();
      for (WebElement childItem : childMenuItems) {
        nameChildMenuItems.add(childItem.getText());
      }

      for (String nameChildItem : nameChildMenuItems) {
        driver.findElement(By.linkText(nameChildItem)).click();
        header = driver.findElement(By.cssSelector("#content h1")).getText();
        assertNotNull(header);
      }
    }

  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
