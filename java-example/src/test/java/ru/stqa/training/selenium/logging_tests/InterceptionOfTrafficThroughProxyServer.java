package ru.stqa.training.selenium.logging_tests;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class InterceptionOfTrafficThroughProxyServer {

  private WebDriver driver;
  private BrowserMobProxy proxy;

  @Before
  public void start() {
    proxy = new BrowserMobProxyServer();
    proxy.start(0);
    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, seleniumProxy);
    driver = new ChromeDriver(caps);

/*    Proxy proxy = new Proxy();
    proxy.setHttpProxy("localhost:8888");
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("proxy", proxy);
    WebDriver driver = new ChromeDriver(caps);
*/
  }

  @Test
  public void testInterceptionOfTrafficThroughProxyServer() {
    proxy.newHar();
    driver.get("https://yandex.ru/");
    Har har = proxy.endHar();
    har.getLog().getEntries().forEach(log -> System.out.println(log.getResponse().getStatus() +
            ":" + log.getRequest().getUrl()));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }

}
