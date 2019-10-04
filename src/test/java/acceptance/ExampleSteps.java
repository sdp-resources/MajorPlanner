package acceptance;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class ExampleSteps {
  @Given("nothing")
  public void nothing() {
  }

  @When("I do nothing")
  public void iDoNothing() {
  }

  @Then("all is good")
  public void allIsGood() {
    assertTrue(true);
  }
}
