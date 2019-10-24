package majorPlanner;

import majorPlanner.request.CreateScheduleRequest;
import org.junit.Test;

public class GatewayBackedAuthorizerTest {
    @Test
    public void userInSessionMatchesOwner()
    {
        CreateScheduleRequest request = new CreateScheduleRequest("user");
    }
}
