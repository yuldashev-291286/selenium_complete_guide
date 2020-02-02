package ru.stqa.training.selenium.layered_architecture_implementation.tests;

import org.junit.After;
import org.junit.Before;
import ru.stqa.training.selenium.layered_architecture_implementation.appmanager.ApplicationManager;

public class TestBase {

  public ApplicationManager app = new ApplicationManager();

  @Before
  public void start() {
    app.init();
  }

  @After
  public void stop() {
    app.stop();
  }

}
