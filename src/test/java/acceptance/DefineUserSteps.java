package acceptance;

import cucumber.api.java.en.Given;
import majorPlanner.entity.Role;
import majorPlanner.entity.User;
import majorPlanner.session.Session;

public class DefineUserSteps {
    @Given("{word} is a logged in {word}")
    public void userHasRole(String name, String role) {
        User user = new User(name, Role.valueOf(role));
        Session session = new Session(null, user);
        TestController.getInstance().addUser(user);
        TestContext.put(name, session);
    }
}
