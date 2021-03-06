package majorPlanner.request;

import majorPlanner.session.Session;
import org.jetbrains.annotations.NotNull;

public abstract class Request {
    private Session session;

    @NotNull
    public static Request createSchedule(String username, String scheduleName, String description) {
        return new CreateScheduleRequest(username, scheduleName, description);
    }

    @NotNull
    public static Request viewSchedule(int scheduleId) {
        return new ViewScheduleRequest(scheduleId);
    }

    @NotNull
    public static Request addCalendarCourse(String course, int schedule, String term, String year) {
        return new AddCourseRequest(course, schedule, term, year);
    }

    @NotNull
    public static Request addTransferCourse(String course, int schedule) {
        return new AddTransferCourseToScheduleRequest(course, schedule);
    }

    @NotNull
    public static Request removeCourseFromSchedule(String courseID, int scheduleID) {
        return new RemoveCourseFromScheduleRequest(courseID, scheduleID);
    }

    public static Request viewCourseList(int requirementId) {
        return new ViewCourseListRequest(requirementId);
    }

    public static Request compareSchedule(int scheduleId) {
        return new CompareScheduleRequest(scheduleId);
    }

    public abstract <T> T accept(RequestVisitor<T> visitor);

    public Session getSession(){
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }
}
