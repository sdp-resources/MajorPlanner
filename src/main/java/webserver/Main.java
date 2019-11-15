package webserver;

import majorPlanner.Controller;
import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.*;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.GatewayBackedInteractorFactory;
import majorPlanner.request.Request;
import majorPlanner.response.Response;

public class Main {
  public static void main(String[] args) {
    WebServer server = new WebServer(6680);
    Gateway gateway = new MemoryGateway();
    gateway.addUser(new User("Joe", Role.User));
    Authorizer authorizer = new Authorizer() {
      public Response authorize(Request request) {
        return Response.ok();
      }
    };
    Controller requestHandler = new Controller(authorizer, new GatewayBackedInteractorFactory(gateway));
    server.setRequestHandler(requestHandler);
    server.setGateway(gateway);
    makeTemporaryData(gateway);
    server.start();
  }

  private static void makeTemporaryData(Gateway gateway) {
    User owner = new User("Joe", Role.User);
    gateway.addUser(owner);
    Schedule schedule = new Schedule(owner, "schedule name", "schedule description");
    schedule.addTransferCourse(new Course("MAT112"));
    schedule.addCourse(new Course("MAT121"), Period.Fall.toString(), Year.Freshman.toString());
    schedule.addCourse(new Course("MAT122"), Period.Winter.toString(), Year.Sophomore.toString());
    schedule.addCourse(new Course("CS220"), Period.Winter.toString(), Year.Sophomore.toString());
    gateway.addSchedule(schedule);
  }
}
