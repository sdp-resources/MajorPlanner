package majorPlanner.response;

public class ValueResponse<T> implements Response {
    private T value;

    public ValueResponse(T value)
    {
        this.value = value;
    }

    @Override
    public boolean containsError() {
        return false;
    }

    public T getValue()
    {
        return value;
    }
}
