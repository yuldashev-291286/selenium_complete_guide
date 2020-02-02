package ru.stqa.training.selenium.layered_architecture_implementation.tests;

import org.junit.Test;

public class AddAndRemoveItemsToCart extends TestBase{

  @Test
  public void testAddAndRemoveItemsToCart() {

    //Добавляем три товара в корзину
    for (int i = 0; i < 3; i++) {
      app.addProductToCart();
    }

    //Удаляем все товары из корзины
    app.removeProductsFromCart();
  }

}
