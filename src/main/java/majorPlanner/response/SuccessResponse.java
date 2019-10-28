package majorPlanner.response;

public class SuccessResponse<T> implements Response {
    private final T value;

    public SuccessResponse(T value) {
        this.value = value;
    }

    @Override
    public boolean containsError() {
        return false;
    }

    public T getValue() {
        return value;
    }

}
