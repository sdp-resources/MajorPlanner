package majorPlanner.response;

public class SuccessfulResponse<T> implements Response {

    @Override
    public boolean containsError() {
        return false;
    }
}
