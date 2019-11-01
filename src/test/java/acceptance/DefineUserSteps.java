package acceptance;

import cucumber.api.java.en.Given;

public class DefineUserSteps {
    @Given("{word} is a {word}")
    public void UserHasRole(String name, String role)
    {
        TestController.getInstance().defineUser(name, role);
    }

    @Given("{word} is logged in")
    public void UserLoggedIn(String userId) {
        TestController.getInstance().defineSession(userId);
    }
}
