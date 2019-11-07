package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.Term;
import majorPlanner.entity.Year;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.ViewScheduleInteractor;
import majorPlanner.interactor.addCourseToScheduleInteractor;
import majorPlanner.interactor.CreateScheduleInteractor;
import majorPlanner.interactor.Interactor;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.request.ViewScheduleRequest;
import majorPlanner.response.*;
import majorPlanner.session.Session;

public class Controller {
    private Gateway gateway;
    private Authorizer authorizer;

    public Controller(Gateway gateway, Authorizer authorizer) {
        this.gateway = gateway;
        this.authorizer = authorizer;
    }

    public Response createSchedule(Session session, String username, String scheduleName, String description) {
        return executeWithSession(new CreateScheduleRequest(username, scheduleName, description),
                new CreateScheduleInteractor(gateway, gateway),
                session);
    }

    public Response viewSchedule(Session session, int scheduleId) {
        return executeWithSession(new ViewScheduleRequest(scheduleId), new ViewScheduleInteractor(gateway), session);
    }

    public Response addCourse(Session session, String course, int schedule, Term term, Year year) {
        return executeWithSession(new AddCourseRequest(course, schedule, term, year),
                new addCourseToScheduleInteractor(gateway, gateway),
                session);
    }

    private Response executeWithSession(Request request, Interactor interactor, Session session) {
        request.setSession(session);
        return execute(request, interactor);
    }

    protected Response execute(Request request, Interactor interactor) {
        Response response = authorizer.authorize(request);
        if (response.containsError()) return response;
        return interactor.execute(request);
    }
}
