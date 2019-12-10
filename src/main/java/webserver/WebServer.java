package webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.Set;

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
        app.get("/check/:scheduleid", this::checkSchedule);
        app.post("/schedule", this::addSchedule);
        app.post("/schedule/:id/course", this::addCourseToSchedule);
        app.post("/schedule/:id/transfer", this::addTransferCourse);
        app.get("/schedule/:id/course/:courseid/delete", this::removeCourse);
        app.get("/requirement/:id/courses", this::getCoursesMeetingRequirement);
    }

    private void getCoursesMeetingRequirement(Context ctx) {
        Session session = getUserSession(ctx);
        requestHandler.viewCourseList(session, Integer.parseInt(ctx.pathParam("id")))
                .handle(o -> {
                    ctx.json(o);
                }, error -> ctx.status(404).result(error));
    }

    private void checkSchedule(Context ctx) {
        Session session = getUserSession(ctx);
        requestHandler.compareSchedule(session,
                                       Integer.parseInt(ctx.pathParam("scheduleid")))
                .handle(o -> {
                    ctx.json(resultsFrom((List<MatchResult>) o));
                }, error -> ctx.status(404).result(error));
    }

    private Object resultsFrom(List<MatchResult> o) {
        ObjectNode root = new ObjectMapper().createObjectNode();
        for (MatchResult matchResult : o) {
            matchResult.handle(
                    (storedReq, course) -> root.put(String.valueOf(storedReq.getId()), course.getId()),
                    (storedReq -> root.set(String.valueOf(storedReq.getId()), null))
            );
        }

        return root;
    }

    private void addTransferCourse(Context ctx) {
        Session session = getUserSession(ctx);
        requestHandler.addTransferCourse(
                session,
                ctx.formParam("course"),
                Integer.parseInt(ctx.pathParam("id")))
                .handle(o -> {
                            ctx.redirect(Path.schedule((Schedule) o));
                        },
                        error -> ctx.status(404).result(error));
    }

    private void removeCourse(Context ctx) {
        Session session = getUserSession(ctx);
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        String courseid = ctx.pathParam("courseid");
        requestHandler.removeCourse(session, courseid, id)
                .handle(
                        o -> {
                            ctx.redirect(Path.schedule((Schedule) o));
                        },
                        error -> ctx.status(404).result(error));
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

    private List<Course> getAvailableCourses() {
        Set<Course> allCourses = gateway.getAllCourses();
        // TODO: Sort alphabetically
        return new ArrayList<>(allCourses);
    }

    public void setGateway(Gateway gateway) {
        // TODO: Eventually don't want this here
        this.gateway = gateway;
    }
}
