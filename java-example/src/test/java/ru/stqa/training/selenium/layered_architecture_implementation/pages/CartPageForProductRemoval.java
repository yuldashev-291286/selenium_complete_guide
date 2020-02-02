package ru.stqa.training.selenium.layered_architecture_implementation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CartPageForProductRemoval extends Page {

  private WebDriver wd;
  private final ThreadLocal<WebDriverWait> wait = ThreadLocal.withInitial(() -> new WebDriverWait(wd, 2));

  public CartPageForProductRemoval(WebDriver wd) {
    super(wd);
    this.wd = wd;
  }

  public void goToCart() {
    WebElement checkout = wd.findElement(By
            .cssSelector(".link[href='http://localhost/litecart/en/checkout']"));
    ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView();", checkout);
    checkout.click();
  }

  public void removeProductsFromCartOneAtTime() {
    List<WebElement> shortcuts = wd.findElements(By.cssSelector(".shortcuts li"));
    for(int i = 0; i < shortcuts.size(); i++) {
      if (i != shortcuts.size() - 1) {
        List<WebElement> dataTable = wd.findElements(By.cssSelector(".dataTable tr"));
        wd.findElement(By.cssSelector(".shortcuts li")).click();
        wd.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
        wait.get().until(numberOfElementsToBe(By.cssSelector(".dataTable tr"), dataTable.size() - 1));
      } else {
        wd.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
        wait.get().until(stalenessOf(wd.findElement(By.cssSelector(".dataTable"))));
      }
    }
  }

}
