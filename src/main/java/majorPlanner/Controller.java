package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.*;
import majorPlanner.request.*;
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

    public Response addCourse(Session session, String course, int schedule, String term, String year) {
        return executeWithSession(new AddCourseRequest(course, schedule, term, year),
                new AddCourseToScheduleInteractor(gateway, gateway),
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

    public Response removeCourse(Session session, String courseID, int scheduleID) {
        RemoveCourseFromScheduleRequest request = new RemoveCourseFromScheduleRequest(courseID, scheduleID);
        RemoveCourseFromScheduleInteractor interactor = new RemoveCourseFromScheduleInteractor(gateway, gateway);
        return executeWithSession(request, interactor, session);
    }
}
