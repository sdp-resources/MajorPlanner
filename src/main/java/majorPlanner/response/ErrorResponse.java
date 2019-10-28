package majorPlanner.response;

import org.jetbrains.annotations.NotNull;

public class ErrorResponse implements Response {
    private static final String INVALID_USER_MESSAGE = "Invalid User";
    private final String error;

    public ErrorResponse(String error)
    {
        this.error = error;
    }

    @NotNull
    public static ErrorResponse invalidUsername() {
        return new ErrorResponse(INVALID_USER_MESSAGE);
    }

    @Override
    public boolean containsError() {
        return true;
    }

    public String getError()
    {
        return error;
    }
}
