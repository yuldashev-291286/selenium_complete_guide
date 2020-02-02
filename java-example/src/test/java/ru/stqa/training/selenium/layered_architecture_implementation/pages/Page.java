package ru.stqa.training.selenium.layered_architecture_implementation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Page {

  protected WebDriver wd;

  public Page(WebDriver wd) {
    this.wd = wd;
  }

  public boolean isElementNotPresent(WebDriver wd, By locator) {
    try {
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      return wd.findElements(locator).size() == 0;
    } finally {
      wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); }
  }

  public boolean isElementPresent(WebDriver wd, By locator) {
    try {
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      return wd.findElements(locator).size() > 0;
    } finally {
      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); }
  }

}
