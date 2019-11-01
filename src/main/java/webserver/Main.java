package webserver;

import majorPlanner.Controller;
import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.Role;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;

public class Main {
  public static void main(String[] args) {
    WebServer server = new WebServer(6680);
    Gateway gateway = new MemoryGateway();
    gateway.addUser(new User("Joe", Role.User));
    Authorizer authorizer = new Authorizer() {
      public Response authorize(Request request) {
        return new SuccessResponse<>(null);
      }
    };
    Controller requestHandler = new Controller(gateway, authorizer);
    server.setRequestHandler(requestHandler);
    server.start();
  }
}
