package majorPlanner.response;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ErrorResponse implements Response {
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

    @Override
    public boolean containsError() {
        return true;
    }

    @Override
    public void handle(Consumer<Object> onSuccess, Consumer<String> onFailure) {
        onFailure.accept(error);
    }

    @Override
    public <T> T handle(Function<Object, T> onSuccess, Function<String, T> onFailure) {
        return onFailure.apply(error);
    }

    public String getError()
    {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" + error + '}';
    }
}
