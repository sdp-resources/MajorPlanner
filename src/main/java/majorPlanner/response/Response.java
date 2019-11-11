package majorPlanner.response;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Response {
    String INVALID_USER_MESSAGE = "Invalid User";
    String INVALID_SCHEDULE_MESSAGE = "Invalid Schedule";
    String INVALID_COURSE_MESSAGE = "Invalid Course";
    String PREVIOUSLY_ADDED_COURSE_MESSAGE = "Course already in schedule";
    String EMPTY_SCHEDULE_MESSAGE = "Cannot delete course from empty schedule";
    String COURSE_NOT_IN_SCHEDULE = "This course can not be found in the schedule";
    String USER_MISMATCH_MESSAGE = "User cannot create a schedule for another user.";
    String SCHEDULE_DOES_NOT_EXIST_MESSAGE = "Schedule does not exist.";
    String COURSE_DOES_NOT_EXIST_MESSAGE = "Course does not exist.";

    @NotNull
    static Response ok() {
        return success(null);
    }

    @NotNull
    static <T> Response success(T value) {
        return new SuccessResponse<>(value);
    }

    @NotNull
    static Response invalidUsername() {
        return new ErrorResponse(INVALID_USER_MESSAGE);
    }

    @NotNull
    static Response invalidCourse() {return new ErrorResponse(INVALID_COURSE_MESSAGE);}

    @NotNull
    static Response invalidSchedule() {return new ErrorResponse(INVALID_SCHEDULE_MESSAGE);}

    @NotNull
    static Response previouslyAddedCourse() {return new ErrorResponse(PREVIOUSLY_ADDED_COURSE_MESSAGE);}

    @NotNull
    static Response emptySchedule() {return new ErrorResponse(EMPTY_SCHEDULE_MESSAGE);}

    @NotNull
    static Response courseNotInSchedule() {return new ErrorResponse(COURSE_NOT_IN_SCHEDULE);}

    @NotNull
    static Response userMismatch() {
        return new ErrorResponse(USER_MISMATCH_MESSAGE);
    }

    @NotNull
    static Response nonExistentCourse() {
        return new ErrorResponse(COURSE_DOES_NOT_EXIST_MESSAGE);
    }

    @NotNull
    static Response nonExistentSchedule() {
        return new ErrorResponse(SCHEDULE_DOES_NOT_EXIST_MESSAGE);
    }

    boolean containsError();
    void handle(Consumer<Object> onSuccess, Consumer<String> onFailure);
    <T> T handle(Function<Object, T> onSuccess, Function<String, T> onFailure);
}
