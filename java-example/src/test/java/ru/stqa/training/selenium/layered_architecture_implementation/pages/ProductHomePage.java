package ru.stqa.training.selenium.layered_architecture_implementation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ProductHomePage extends Page {

  private WebDriver wd;
  private final ThreadLocal<WebDriverWait> wait = ThreadLocal.withInitial(() -> new WebDriverWait(wd, 2));

  public ProductHomePage(WebDriver wd) {
    super(wd);
    this.wd = wd;
  }

  public ProductHomePage openHomePage() {
    wd.navigate().to("http://localhost/litecart/en/");
    wait.get().until(titleIs("Online Store | My Store"));
    return this;
  }

}
