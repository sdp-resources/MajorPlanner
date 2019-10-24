package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.Role;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Test;

public class GatewayBackedAuthorizerTest {
    @Test
    public void userInSessionMatchesOwner()
    {
        CreateScheduleRequest request = new CreateScheduleRequest("dantgeo");
        request.setSession(new Session(null, "dantgeo", Role.User));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        Response response = authorizer.authorize(request);

    }
}
