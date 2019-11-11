package majorPlanner.response;

import java.util.function.Consumer;
import java.util.function.Function;

public class SuccessResponse<T> implements Response {
    private final T value;

    SuccessResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean containsError() {
        return false;
    }

    @Override
    public void handle(Consumer<Object> onSuccess, Consumer<String> onFailure) {
        onSuccess.accept(value);
    }

    @Override
    public <T> T handle(Function<Object, T> onSuccess, Function<String, T> onFailure) {
        return onSuccess.apply(value);
    }

}
