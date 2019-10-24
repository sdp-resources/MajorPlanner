package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.Role;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class GatewayBackedAuthorizerTest {
    @Test
    public void userInSessionMatchesOwner()
    {
        CreateScheduleRequest request = new CreateScheduleRequest("dantgeo");
        request.setSession(new Session(null, "dantgeo", Role.User));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        Response response = authorizer.authorize(request);
        Assert.assertFalse(response.containsError());
    }

    @Test
    public void userInSessionDoesNotMatchOwner(){
        CreateScheduleRequest request = new CreateScheduleRequest("dantgeo");
        request.setSession(new Session(null, "marsht", Role.User));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        Response response = authorizer.authorize(request);
        Assert.assertTrue(response.containsError());
        Assert.assertEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, ((ErrorResponse)response).getError());
    }

    @Test
    public void adminInSessionIgnoresRequirements(){
        CreateScheduleRequest request = new CreateScheduleRequest("dantgeo");
        request.setSession(new Session(null, "marsht", Role.Admin));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        Response response = authorizer.authorize(request);
        Assert.assertFalse(response.containsError());
    }
}
