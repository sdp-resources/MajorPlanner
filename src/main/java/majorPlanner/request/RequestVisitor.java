package majorPlanner.request;

public interface RequestVisitor<T> {
    T visit(CreateScheduleRequest request);
    T visit(AddCourseRequest request);
    T visit(ViewScheduleRequest request);
    T visit(RemoveCourseFromScheduleRequest request);
    default T visit(Request request) { return request.accept(this); };
}
