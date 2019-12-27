package ru.stqa.training.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class FirstTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void myFirstTest() {
    driver.navigate().to("http://www.google.ru");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.className("hOoLGe")).click();
    driver.findElement(By.id("K32")).click();
    driver.findElement(By.name("btnK")).click();
    wait.until(titleIs("webdriver - ����� � Google"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
