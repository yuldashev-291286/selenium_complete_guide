package ru.stqa.training.selenium.actions_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AddingProductUnderAdministrator {

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
  public void testAddingProductUnderAdministrator() {
    driver.findElement(By.linkText("Catalog")).click();
    wait.until(titleIs("Catalog | My Store"));
    driver.findElement(By.cssSelector(".button[href='http://localhost/litecart/admin/" +
                    "?category_id=0&app=catalog&doc=edit_product']")).click();
    wait.until(titleIs("Add New Product | My Store"));

    String nameDuck = nameDuckGenerator();
    driver.findElement(By.xpath("//input[@name='status' and @value=1]")).click();
    driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(nameDuck);
    driver.findElement(By.cssSelector("input[name='code']")).sendKeys("01");
    driver.findElement(By.xpath("//input[@name='product_groups[]' and @value='1-3']")).click();
    driver.findElement(By.xpath("//input[@name='quantity' and @type='number']")).clear();
    driver.findElement(By.xpath("//input[@name='quantity' and @type='number']")).sendKeys("1");

    File purpleDuckPhoto = new File("src/test/resources/purple_duck.png");
    driver.findElement(By.xpath("//input[@name='new_images[]' and @type='file']"))
            .sendKeys(purpleDuckPhoto.getAbsolutePath());

    driver.findElement(By.cssSelector("input[name='date_valid_from']"))
            .sendKeys("2020-01-13");
    driver.findElement(By.cssSelector("input[name='date_valid_to']"))
            .sendKeys("2020-01-20");

//    setDatepicker(driver, "input[name='date_valid_from']", "11.01.2020");
//    setDatepicker(driver, "input[name='date_valid_to']", "18.01.2020");

    JavascriptExecutor js = (JavascriptExecutor) driver;
    driver.findElement(By.cssSelector("a[href='#tab-information']")).click();

    Select selectManufacturer = new Select(driver.findElement(By.name("manufacturer_id")));
    js.executeScript("arguments[0].selectedIndex = 1; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectManufacturer);

    Select selectSupplier = new Select(driver.findElement(By.name("supplier_id")));
    js.executeScript("arguments[0].selectedIndex = 1; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectSupplier);

    driver.findElement(By.name("keywords")).sendKeys("Purple duck");
    driver.findElement(By.name("short_description[en]")).sendKeys("Purple duck");
    driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("Purple duck");
    driver.findElement(By.name("head_title[en]")).sendKeys("Purple duck");
    driver.findElement(By.name("meta_description[en]")).sendKeys("Purple duck");

    driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
    driver.findElement(By.name("purchase_price")).clear();
    driver.findElement(By.name("purchase_price")).sendKeys("50");

    Select selectCurrencyOfPurchase = new Select(driver.findElement(By.name("purchase_price_currency_code")));
    js.executeScript("arguments[0].selectedIndex = 1; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectCurrencyOfPurchase);

    Select selectTaxClass = new Select(driver.findElement(By.name("tax_class_id")));
    js.executeScript("arguments[0].selectedIndex = 1; " +
            "arguments[0].dispatchEvent(new Event('change'))", selectTaxClass);

    driver.findElement(By.name("prices[USD]")).sendKeys("50");
    driver.findElement(By.name("gross_prices[USD]")).clear();
    driver.findElement(By.name("gross_prices[USD]")).sendKeys("50");
    driver.findElement(By.name("prices[EUR]")).sendKeys("45");
    driver.findElement(By.name("gross_prices[EUR]")).clear();
    driver.findElement(By.name("gross_prices[EUR]")).sendKeys("45");
    driver.findElement(By.name("save")).click();

    List<WebElement> soldDucks = driver.findElements(By.cssSelector(".dataTable .row"));
    List<String> namesOfDucksSold = new ArrayList<>();
    for (WebElement soldDuck : soldDucks) {
      List<WebElement> cells = soldDuck.findElements(By.tagName("td"));
      namesOfDucksSold.add(cells.get(2).getText());
    }
    boolean checkingForDucks = false;
    for (String nameOfDucksSold : namesOfDucksSold) {
      if (nameOfDucksSold.equals(nameDuck) == true) {
        checkingForDucks = true;
        break;
      }
    }
    assertTrue(checkingForDucks);
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  public void setDatepicker(WebDriver driver, String cssSelector, String date) {
    ((JavascriptExecutor) driver).executeScript(
            String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
  }

  public String nameDuckGenerator() {
    long now = System.currentTimeMillis();
    String nameDuck = String.format("Test duck number %s", now);
    return nameDuck;
  }

}
