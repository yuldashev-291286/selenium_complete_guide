package ru.stqa.training.selenium.layered_architecture_implementation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductPageForAddToCart extends Page {

  private WebDriver wd;
  private final ThreadLocal<WebDriverWait> wait = ThreadLocal.withInitial(() -> new WebDriverWait(wd, 2));;

  public ProductPageForAddToCart(WebDriver wd) {
    super(wd);
    this.wd = wd;
  }

  public List<WebElement> mostPopularProducts() {
    List<WebElement> mostPopularProducts = wd.findElements(By.cssSelector("#box-most-popular li"));
    return mostPopularProducts;
  }

  public void chooseFirstProduct(List<WebElement> mostPopularProducts) {
    mostPopularProducts.get(0).click();
  }

  public int productsCounterBefore() {
    int productsCounterBefore = Integer.parseInt(wd.findElement(By
            .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity")).getText());
    return productsCounterBefore;
  }

  public void addProduct() {
    if (!isElementNotPresent(wd, By.cssSelector("select[name='options[Size]']"))) {
      Select selectOptions = new Select(wd.findElement(By.cssSelector("select[name='options[Size]']")));
      ((JavascriptExecutor) wd).executeScript("arguments[0].selectedIndex = 1; " +
              "arguments[0].dispatchEvent(new Event('change'))", selectOptions);
      wd.findElement(By.cssSelector("button[name='add_cart_product']")).click();
    } else {
      wd.findElement(By.cssSelector("button[name='add_cart_product']")).click();
    }
  }

  public void dialogClose() {
    Alert alert = wait.get().until(alertIsPresent());
    alert.accept();
  }

  public void refreshPage() {
    wd.navigate().refresh();
  }

  public void awaitingIncreaseInBasketCounter(int productsCounterBefore) {
    wait.get().until(attributeContains(wd.findElement(By
                    .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity"))
            , "textContent", Integer.toString(productsCounterBefore + 1)));
    wait.get().until(textToBePresentInElement(wd.findElement(By
                    .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity"))
            , Integer.toString(productsCounterBefore + 1)));
  }

  public int productsCounterAfter() {
    int productsCounterAfter = Integer.parseInt(wd.findElement(By
            .cssSelector("a[href='http://localhost/litecart/en/checkout'] .quantity")).getText());
    return productsCounterAfter;
  }

  public void comparisonOfNumberOfProductsBeforeAndAfterAdding(int productsCounterBefore, int productsСounterAfter) {
    assertEquals("Product counter not increased.", productsCounterBefore + 1
            , productsСounterAfter);
  }

  public void goToHomePage() {
    wd.navigate().back();
  }

}
