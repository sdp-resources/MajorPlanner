package majorPlanner.request;

public interface RequestVisitor<T> {
    T visit(CreateScheduleRequest request);
    T visit(AddCourseRequest request);
    T visit(ViewScheduleRequest request);
}
