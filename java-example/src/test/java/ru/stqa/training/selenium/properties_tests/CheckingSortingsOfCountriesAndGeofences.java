package ru.stqa.training.selenium.properties_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckingSortingsOfCountriesAndGeofences {

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
  }

  @Test
  public void testCountrySortingCheck() {
    driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
    wait.until(titleIs("Countries | My Store"));

    List<WebElement> countries = driver.findElements(By.cssSelector(".row input"));
    Map<Integer, String> countryNames = new HashMap<>();
    for (WebElement country : countries) {
      countryNames.put(Integer.parseInt(country.getAttribute("value")), country.getText());
    }
//    Collections.sort(countryNames);
    assertTrue(isSortedMap(countryNames));
  }

  @Test
  public void testCountryZoneSortingCheck() {
    driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
    wait.until(titleIs("Countries | My Store"));

    List<WebElement> countries = driver.findElements(By.cssSelector(".row"));
    List<String> countriesWithZones = new ArrayList<>();
    for (WebElement country : countries) {
      List<WebElement> cells = country.findElements(By.tagName("td"));
      if (Integer.parseInt(cells.get(5).getText()) != 0) {
        countriesWithZones.add(cells.get(4).getText());
      }
    }

    Map<Integer, String> zoneNames = new HashMap<>();
    for (String countryName : countriesWithZones) {
      driver.findElement(By.linkText(countryName)).click();
      List<WebElement> zones = driver.findElements(By.cssSelector("#table-zones tr"));
      zones.remove(0);
      zones.remove(zones.size() - 1);
      for (WebElement zone : zones) {
        List<WebElement> cells = zone.findElements(By.tagName("td"));
        zoneNames.put(Integer.parseInt(cells.get(0).getText()), cells.get(2).getText());
      }
      assertTrue(isSortedMap(zoneNames));
      driver.navigate().back();
    }
  }

  @Test
  public void testGeofenceSortingCheck() {
    driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    wait.until(titleIs("Geo Zones | My Store"));

    List<WebElement> geofences = driver.findElements(By.cssSelector(".row"));
    List<String> namesOfGeofences = new ArrayList<>();
    for (WebElement geofence : geofences) {
      List<WebElement> cells = geofence.findElements(By.tagName("td"));
      namesOfGeofences.add(cells.get(2).getText());
    }

    Map<Integer, String> geofencesNames = new HashMap<>();
    for (String geofenceName : namesOfGeofences) {
      driver.findElement(By.linkText(geofenceName)).click();
      List<WebElement> zones = driver.findElements(By.cssSelector("#table-zones tr"));
      zones.remove(0);
      zones.remove(zones.size() - 1);
      for (WebElement zone : zones) {
        List<WebElement> cells = zone.findElements(By.tagName("td"));
        geofencesNames.put(Integer.parseInt(zone.getAttribute("rowIndex"))
                , cells.get(2).findElement(By.cssSelector("option[selected='selected']")).getText());
      }

      Iterator<Map.Entry<Integer, String>> entries = geofencesNames.entrySet().iterator();
      while (entries.hasNext()) {
        Map.Entry<Integer, String> entry = entries.next();
        System.out.println(entry.getKey() + "   " + entry.getValue());
      }

      assertTrue(isSortedMap(geofencesNames));
      driver.navigate().back();
    }
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }


  public boolean isSortedMap(Map<Integer, String> countryNames) {
    Integer[] countryIdentifiers = new Integer[countryNames.size()];
    countryIdentifiers = countryNames.keySet().toArray(countryIdentifiers);
    Integer previous = countryIdentifiers[0];
    for (int i = 1; i < countryIdentifiers.length; i++) {
      if (previous.compareTo(countryIdentifiers[i]) != -1) {
        return false;
      }
      previous = countryIdentifiers[i];
    }
    return true;
  }

  public boolean isSortedList(List<String> countryNames) {
    String[] names = new String[countryNames.size()];
    names = countryNames.toArray(names);
    String previous = names[0];
    for (int i = 1; i < names.length; i++) {
      if (previous.compareTo(names[i]) != -1) {
        return false;
      }
      previous = names[i];
    }
    return true;
  }

}
