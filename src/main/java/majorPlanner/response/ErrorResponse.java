package majorPlanner.response;

import org.jetbrains.annotations.NotNull;

public class ErrorResponse implements Response {
    private static final String INVALID_USER_MESSAGE = "Invalid User";
    private static final String INVALID_SCHEDULE_MESSAGE = "Invalid Schedule";
    private static final String INVALID_COURSE_MESSAGE = "Invalid Course";
    private static final String PREVIOUSLY_ADDED_COURSE_MESSAGE = "Course already in schedule";
    private final String error;

    public ErrorResponse(String error)
    {
        this.error = error;
    }

    @NotNull
    public static ErrorResponse invalidUsername() {
        return new ErrorResponse(INVALID_USER_MESSAGE);
    }

    @NotNull
    public static ErrorResponse invalidCourse() {return new ErrorResponse(INVALID_COURSE_MESSAGE);}

    @NotNull
    public static ErrorResponse invalidSchedule() {return new ErrorResponse(INVALID_SCHEDULE_MESSAGE);}

    @NotNull
    public static ErrorResponse previouslyAddedCourse() {return new ErrorResponse(PREVIOUSLY_ADDED_COURSE_MESSAGE);}

    @Override
    public boolean containsError() {
        return true;
    }

    public String getError()
    {
        return error;
    }
}
