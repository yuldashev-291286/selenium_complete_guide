package ru.stqa.training.selenium.properties_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckOpeningOfCorrectProductPage {

  private WebDriver driver;
  private WebDriverWait wait;

  private List<WebElement> campaigns;
  private WebElement productFromMainPage;

  @Before
  public void start() {
    driver = new FirefoxDriver();
//    driver = new ChromeDriver();
//    driver = new InternetExplorerDriver();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 1);

    driver.navigate().to("http://localhost/litecart/en/");
    wait.until(titleIs("Online Store | My Store"));

    campaigns = driver.findElements(By.cssSelector("#box-campaigns .products li"));
    productFromMainPage = campaigns.get(0).findElement(By.cssSelector(".link"));
  }

  @Test
  public void priceComparisonOfProducts() {
    Map<String, String> productFromMainPageInformation = new HashMap<>();

    productFromMainPageInformation.put("product name"
            , productFromMainPage.getAttribute("title"));

    productFromMainPageInformation.put("regular price"
            , productFromMainPage.findElement(By.cssSelector(".regular-price")).getText());

    productFromMainPageInformation.put("campaign price"
            , productFromMainPage.findElement(By.cssSelector(".campaign-price")).getText());

    driver.navigate().to(productFromMainPage.getAttribute("href"));

    Map<String, String> productFromProductPageInformation = new HashMap<>();

    productFromProductPageInformation.put("product name"
            , driver.findElement(By.cssSelector("#box-product .title")).getText());

    productFromProductPageInformation.put("regular price"
            , driver.findElement(By.cssSelector(".regular-price")).getText());

    productFromProductPageInformation.put("campaign price"
            , driver.findElement(By.cssSelector(".campaign-price")).getText());


    assertThat(productFromMainPageInformation.get("product name")
            , equalTo(productFromProductPageInformation.get("product name")));

    assertThat(productFromMainPageInformation.get("regular price")
            , equalTo(productFromProductPageInformation.get("regular price")));

    assertThat(productFromMainPageInformation.get("campaign price")
            , equalTo(productFromProductPageInformation.get("campaign price")));
  }

  @Test
  public void priceColorRGBaComparisonOfProducts() {
    String regularPriceFromMainPageColorRGBa = productFromMainPage
            .findElement(By.cssSelector(".regular-price")).getCssValue("color");
    List<Integer> rgbaColorRegularPriceFromMainPage = pageItemOptionsRGBa(regularPriceFromMainPageColorRGBa);

    assertTrue(rgbaColorRegularPriceFromMainPage.get(0).equals(rgbaColorRegularPriceFromMainPage.get(1)));
    assertTrue(rgbaColorRegularPriceFromMainPage.get(1).equals(rgbaColorRegularPriceFromMainPage.get(2)));

    String campaignPriceFromMainPageColorRGBa = productFromMainPage
            .findElement(By.cssSelector(".campaign-price")).getCssValue("color");
    List<Integer> rgbaColorCampaignPriceFromMainPage = pageItemOptionsRGBa(campaignPriceFromMainPageColorRGBa);

    assertTrue(rgbaColorCampaignPriceFromMainPage.get(1).equals(0));
    assertTrue(rgbaColorCampaignPriceFromMainPage.get(2).equals(0));

    driver.navigate().to(productFromMainPage.getAttribute("href"));

    String regularPriceFromProductPageColorRGBa = driver
            .findElement(By.cssSelector(".regular-price")).getCssValue("color");
    List<Integer> rgbaColorRegularPriceFromProductPage = pageItemOptionsRGBa(regularPriceFromProductPageColorRGBa);

    assertTrue(rgbaColorRegularPriceFromProductPage.get(0).equals(rgbaColorRegularPriceFromProductPage.get(1)));
    assertTrue(rgbaColorRegularPriceFromProductPage.get(1).equals(rgbaColorRegularPriceFromProductPage.get(2)));

    String campaignPriceFromProductPageColorRGBa = driver
            .findElement(By.cssSelector(".campaign-price")).getCssValue("color");
    List<Integer> rgbaColorCampaignPriceFromProductPage = pageItemOptionsRGBa(campaignPriceFromProductPageColorRGBa);

    assertTrue(rgbaColorCampaignPriceFromProductPage.get(1).equals(0));
    assertTrue(rgbaColorCampaignPriceFromProductPage.get(2).equals(0));
  }

  @Test
  public void priceSizeComparisonOfProducts() {
    Dimension dimensionRegularPriceFromMainPage = productFromMainPage
            .findElement(By.cssSelector(".regular-price")).getSize();

    Dimension dimensionCampaignPriceFromMainPage = productFromMainPage
            .findElement(By.cssSelector(".campaign-price")).getSize();

    assertTrue(dimensionCampaignPriceFromMainPage.height > dimensionRegularPriceFromMainPage.height);
    assertTrue(dimensionCampaignPriceFromMainPage.width > dimensionRegularPriceFromMainPage.width);

    driver.navigate().to(productFromMainPage.getAttribute("href"));

    Dimension dimensionRegularPriceFromProductPage = driver
            .findElement(By.cssSelector(".regular-price")).getSize();

    Dimension dimensionCampaignPriceFromProductPage = driver
            .findElement(By.cssSelector(".campaign-price")).getSize();

    assertTrue(dimensionCampaignPriceFromProductPage.height > dimensionRegularPriceFromProductPage.height);
    assertTrue(dimensionCampaignPriceFromProductPage.width > dimensionRegularPriceFromProductPage.width);
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  public List<Integer> pageItemOptionsRGBa(String colorRGBa) {
    String subString = colorRGBa.substring(colorRGBa.indexOf("("));
    String[] stringsColorRGBa = subString.split("[\\D]");

    List<Integer> pageItemOptionsRGBa = new ArrayList<>();
    for (String rgba : stringsColorRGBa) {
      if (rgba.length() != 0) {
        pageItemOptionsRGBa.add(Integer.parseInt(rgba));
      }
    }
    return pageItemOptionsRGBa;
  }

}
