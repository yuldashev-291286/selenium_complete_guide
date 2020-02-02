package ru.stqa.training.selenium.layered_architecture_implementation.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.training.selenium.layered_architecture_implementation.pages.CartPageForProductRemoval;
import ru.stqa.training.selenium.layered_architecture_implementation.pages.ProductHomePage;
import ru.stqa.training.selenium.layered_architecture_implementation.pages.ProductPageForAddToCart;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private WebDriver driver;

  private ProductHomePage productHomePage;
  private ProductPageForAddToCart productPageForAddToCart;
  private CartPageForProductRemoval cartPageForProductRemoval;

  public void init() {
    driver = new FirefoxDriver();

    productHomePage = new ProductHomePage(driver);
    productPageForAddToCart = new ProductPageForAddToCart(driver);
    cartPageForProductRemoval = new CartPageForProductRemoval(driver);

    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
  }

  public void stop() {
    driver.quit();
    driver = null;
  }

  public void addProductToCart() {
    //Открываем страницу товаров
    productHomePage.openHomePage();

    //Выбираем все популярные товары
    List<WebElement> mostPopularProducts = productPageForAddToCart.mostPopularProducts();

    //Открываем первый товар из популярных
    productPageForAddToCart.chooseFirstProduct(mostPopularProducts);

    //Запоминаем количество товаров в корзине до добавления
    int productsCounterBefore = productPageForAddToCart.productsCounterBefore();

    //Добавляем товар в корзину
    productPageForAddToCart.addProduct();

    //Закрываем диалоговое окно
    productPageForAddToCart.dialogClose();

    //Обновляем страницу
    productPageForAddToCart.refreshPage();

    //Ожидаем когда счетчик товаров к корзине увеличится на единицу
    productPageForAddToCart.awaitingIncreaseInBasketCounter(productsCounterBefore);

    //Запоминаем количество товаров в корзине после добавления
    int productsCounterAfter = productPageForAddToCart.productsCounterAfter();

    //Проверяем увеличение счетчика товаров в корзине
    productPageForAddToCart.comparisonOfNumberOfProductsBeforeAndAfterAdding(productsCounterBefore, productsCounterAfter);

    //Переходим на страницу товаров
    productPageForAddToCart.goToHomePage();
  }


  public void removeProductsFromCart() {
    //Переходим в корзину
    cartPageForProductRemoval.goToCart();

    //Удаляем товары из корзины по одному
    cartPageForProductRemoval.removeProductsFromCartOneAtTime();
  }

}
