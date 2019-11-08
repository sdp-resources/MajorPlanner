package majorPlanner.response;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Response {
    boolean containsError();
    void handle(Consumer<Object> onSuccess, Consumer<String> onFailure);
    <T> T handle(Function<Object, T> onSuccess, Function<String, T> onFailure);
}
