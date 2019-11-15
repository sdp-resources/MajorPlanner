package webserver;

import io.javalin.Javalin;
import io.javalin.http.Context;
import majorPlanner.Controller;
import majorPlanner.entity.*;
import majorPlanner.gateway.Gateway;
import majorPlanner.session.Session;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class WebServer {
    private static Javalin app;
    private int port;
    private Controller requestHandler;
    private Gateway gateway;

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
        app.post("/schedule/:id/course", this::addCourseToSchedule);
    }

    private void showSchedule(Context ctx) {
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        Session session = getUserSession(ctx);
        requestHandler.viewSchedule(session, id)
                .handle(
                        schedule -> {
                            Map<String, Object> model = model("schedule", schedule,
                                                              "terms", getPeriods(),
                                                              "years", getYears(),
                                                              "availableCourses", getAvailableCourses());
                            return ctx.render("/schedule.twig", model);
                        },
                        error -> ctx.status(404).result(error));
    }

    @NotNull
    private Year[] getYears() {
        return new Year[]{Year.Freshman, Year.Sophomore, Year.Junior, Year.Senior};
    }

    @NotNull
    private Period[] getPeriods() {
        return new Period[]{Period.Fall, Period.Winter, Period.Spring};
    }

    private void addSchedule(Context ctx) {
        Session session = getUserSession(ctx);
        requestHandler.createSchedule(
                session,
                ctx.formParam("ownerid"),
                ctx.formParam("name"),
                ctx.formParam("description"))
                .handle(o -> {
                            ctx.redirect(Path.schedule((Schedule) o));
                        },
                        error -> ctx.status(404).result(error));
    }

    private void addCourseToSchedule(Context ctx) {
        Session session = getUserSession(ctx);
        requestHandler.addCourse(
                session,
                ctx.formParam("course"),
                Integer.parseInt(ctx.pathParam("id")),
                ctx.formParam("period"),
                ctx.formParam("year"))
                .handle(o -> {
                            ctx.redirect(Path.schedule((Schedule) o));
                        },
                        error -> ctx.status(404).result(error));

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

//    private Schedule getSchedule(Integer id) {
        // TODO: Make proper request when method is available

//        User owner = new User("Joe", Role.User);
//        Schedule schedule = new Schedule(owner, "schedule name", "schedule description");
//        schedule.addTransferCourse(new Course("MAT112"));
//        schedule.addCourse(new Course("MAT121"), Period.Fall.toString(), Year.Freshman.toString());
//        schedule.addCourse(new Course("MAT122"), Period.Winter.toString(), Year.Sophomore.toString());
//        schedule.addCourse(new Course("CS220"), Period.Winter.toString(), Year.Sophomore.toString());
//        return schedule;
//    }

    private List<Course> getAvailableCourses() {
        String[] courseNames = {"MAT121", "MAT122", "CS220", "CS223", "CS225"};
        ArrayList<Course> courseList = new ArrayList<>();
        for (String courseName : courseNames) {
            Course course = new Course(courseName);
            gateway.addCourse(course);
            courseList.add(course);
        }
        return courseList;
    }

    public void setGateway(Gateway gateway) {
        // TODO: Eventually don't want this here
        this.gateway = gateway;
    }
}
