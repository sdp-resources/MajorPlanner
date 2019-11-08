package webserver;

import io.javalin.Javalin;
import io.javalin.http.Context;
import majorPlanner.Controller;
import majorPlanner.entity.*;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.response.SuccessResponse;
import majorPlanner.session.Session;
import org.jetbrains.annotations.NotNull;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class WebServer {
  private static Javalin app;
  private int port;
  private Controller requestHandler;

  public WebServer(int port) {
    this.port = port;
    app = Javalin.create();
    setup();
  }

  public void start() {
    app.start(port);
  }

  Controller getRequestHandler() {
    return requestHandler;
  }

  void setRequestHandler(Controller requestHandler) {
    this.requestHandler = requestHandler;
  }

  protected void setup() {
    app.get("/", this::getIndex);
    app.get("/schedule/:id", this::showSchedule);
    app.post("/schedule", this::addSchedule);
  }

  private void showSchedule(Context ctx) {
    Integer id = Integer.valueOf(ctx.pathParam("id"));
    Schedule schedule = getSchedule(id);
    ctx.render("/schedule.twig",
               model("schedule", schedule,
                     "terms", new Term[]{Term.Fall, Term.Winter, Term.Spring},
                     "years", new Year[]{Year.Freshman, Year.Sophomore, Year.Junior, Year.Senior}));
  }

  private void addSchedule(Context ctx) {
    Session session = getUserSession(ctx);
    Response response = requestHandler.createSchedule(
        session,
        ctx.formParam("ownerid"),
        ctx.formParam("name"),
        ctx.formParam("description"));
    if (response.containsError()) {
      System.out.println("Exiting with error");
      ctx.status(404).result(((ErrorResponse) response).getError());
    } else {
      SuccessResponse<Schedule> successResponse = (SuccessResponse<Schedule>) response;
      Schedule schedule = successResponse.getValue();
      ctx.redirect("/schedule/" + schedule.getID());
    }
  }

  private void getIndex(Context ctx) {
    ctx.render("/index.twig", model());
  }

  @NotNull
  private Session getUserSession(Context ctx) {
    // TODO: Not the right way to get the session
    return getSession(ctx.formParam("ownerid"));
  }

  @NotNull
  private Session getSession(String userid) {
    return new Session("token", new User(userid, Role.User));
  }

  private Schedule getSchedule(Integer id) {
    // TODO: Make proper request when method is available
    User owner = new User("Joe", Role.User);
    Schedule schedule = new Schedule(owner, "schedule name", "schedule description");
    schedule.addCourse(new Course("MAT121"), Term.Fall.toString(), Year.Freshman.toString());
    schedule.addCourse(new Course("MAT122"), Term.Winter.toString(), Year.Sophomore.toString());
    return schedule;
  }
}
