package webserver;

import majorPlanner.Controller;
import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.GatewayBackedInteractorFactory;
import mock.AcceptingAuthorizer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class WebServerRoutesTest extends ServerTestBase {

  public static final String USER_ID = "anOwnerId";
  private WebClient client;
  private Controller requestHandler;
  private Gateway gateway = new MemoryGateway();
  private Authorizer authorizer = new AcceptingAuthorizer();
  private Controller controller = new Controller(authorizer, new GatewayBackedInteractorFactory(gateway));

  @Before
  public void setUp() {
    gateway.addUser(new User(USER_ID));
    requestHandler = spy(controller);
    setRequestHandler(requestHandler);
  }

  @Test
  public void canStartWebServer() {
    client = new WebClient("/", "GET", false);
    client.execute();
    client.assertResponseCodeIs(200);
  }

  @Test
  public void scheduleSubmissionCreatesSchedule() {
    String scheduleName = "the name";
    String scheduleDescription = "the description";
    String ownerId = USER_ID;
    Map<String, String> params =  new HashMap<>();
    params.put("name", scheduleName);
    params.put("description", scheduleDescription);
    params.put("ownerid", ownerId);
    client = new WebClient("/schedule", "POST", params, false);
    verify(requestHandler).createSchedule(any(), eq(ownerId),
                                          eq(scheduleName), eq(scheduleDescription));
    client.assertResponseCodeIs(302);
    // TODO: Check redirect destination?
  }
}
