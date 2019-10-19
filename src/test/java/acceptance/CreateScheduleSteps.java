package acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class CreateScheduleSteps {
    @Given("{word} is a {word} and logged in")
    public void HasRoleAndLoggedIn(String username, String role) {

    }

    @When("{word} creates a schedule called {string}")
    public void CreateSchedule(String username, String scheduleName) {
    }

    @Then("there exists a schedule called {string} owned by {word}")
    public void ScheduleExists(String scheduleName, String username) {
    }
}
