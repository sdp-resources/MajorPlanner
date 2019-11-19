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
        Request request = Request.createSchedule(username, scheduleName, description);
        return executeWithSession(request,
                                  interactorFactory.getInteractorFor(request),
                                  session);
    }

    public Response viewSchedule(Session session, int scheduleId) {
        Request request = Request.viewSchedule(scheduleId);
        return executeWithSession(request, interactorFactory.getInteractorFor(request), session);
    }

    public Response addCourse(Session session, String course, int schedule, String term, String year) {
        Request request = Request.addCalendarCourse(course, schedule, term, year);
        Interactor interactor = interactorFactory.getInteractorFor(request);
        return executeWithSession(request, interactor, session);
    }

    private Response executeWithSession(Request request, Interactor interactor, Session session) {
        request.setSession(session);
        return execute(request, interactor);
    }

    protected Response execute(Request request, Interactor interactor) {
        Response response = authorizer.authorize(request);
        if (response.containsError()) return response;
        return interactor.execute();
    }

    public Response removeCourse(Session session, String courseID, int scheduleID) {
        Request request = Request.removeCourseFromSchedule(courseID, scheduleID);
        return executeWithSession(request, interactorFactory.getInteractorFor(request), session);
    }

    public Response addTransferCourse(Session session, String courseId, int scheduleId) {
        Request request = Request.addTransferCourse(courseId, scheduleId);
        return executeWithSession(request, interactorFactory.getInteractorFor(request), session);
    }
}
