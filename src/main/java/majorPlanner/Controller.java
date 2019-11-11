package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.interactor.Interactor;
import majorPlanner.interactor.InteractorFactory;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import majorPlanner.session.Session;

public class Controller {
    private final InteractorFactory interactorFactory;
    private Authorizer authorizer;

    public Controller(Authorizer authorizer, InteractorFactory interactorFactory) {
        this.interactorFactory = interactorFactory;
        this.authorizer = authorizer;
    }

    public Response createSchedule(Session session, String username, String scheduleName, String description) {
        return executeWithSession(Request.createSchedule(username, scheduleName, description),
                                  interactorFactory.createSchedule(),
                                  session);
    }

    public Response viewSchedule(Session session, int scheduleId) {
        return executeWithSession(Request.viewSchedule(scheduleId), interactorFactory.viewSchedule(), session);
    }

    public Response addCourse(Session session, String course, int schedule, String term, String year) {
        return executeWithSession(Request.addSchedule(course, schedule, term, year),
                                  interactorFactory.addCourseToSchedule(),
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
        Request request = Request.removeCourseFromSchedule(courseID, scheduleID);
        Interactor interactor = interactorFactory.removeCourseFromSchedule();
        return executeWithSession(request, interactor, session);
    }

}
