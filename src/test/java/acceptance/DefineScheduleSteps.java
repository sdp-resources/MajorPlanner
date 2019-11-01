package acceptance;

import cucumber.api.java.en.Given;

public class DefineScheduleSteps {
    @Given("a schedule, {word}, with owner {word}")
    public void defineSchedule(String name, String owner)
    {
        TestController.getInstance().defineSchedule(name, owner);
    }
}
