package majorPlanner.request;

public interface RequestVisitor<T> {
    T visitCreateScheduleRequest(CreateScheduleRequest createScheduleRequest);
}
