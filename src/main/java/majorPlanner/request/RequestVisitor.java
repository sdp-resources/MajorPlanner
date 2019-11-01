package majorPlanner.request;

public interface RequestVisitor<T> {
    T visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest);

    T visitAddCourseRequest(AddCourseRequest addCourseRequest);
}
