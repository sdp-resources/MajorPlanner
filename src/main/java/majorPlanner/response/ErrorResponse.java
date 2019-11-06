package majorPlanner.response;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ErrorResponse implements Response {
    private static final String INVALID_USER_MESSAGE = "Invalid User";
    private static final String INVALID_SCHEDULE_MESSAGE = "Invalid Schedule";
    private static final String INVALID_COURSE_MESSAGE = "Invalid Course";
    private static final String PREVIOUSLY_ADDED_COURSE_MESSAGE = "Course already in schedule";
    private static final String EMPTY_SCHEDULE_MESSAGE = "Cannot delete course from empty schedule";
    private static final String COURSE_NOT_IN_SCHEDULE = "This course can not be found in the schedule";
    private final String error;

    public ErrorResponse(String error)
    {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
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

    @NotNull
    public static ErrorResponse emptySchedule() {return new ErrorResponse(EMPTY_SCHEDULE_MESSAGE);}

    @NotNull
    public static ErrorResponse courseNotInSchedule() {return new ErrorResponse(COURSE_NOT_IN_SCHEDULE);}

    @Override
    public boolean containsError() {
        return true;
    }

    public String getError()
    {
        return error;
    }
}
