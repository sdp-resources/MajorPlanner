package majorPlanner.response;

public class SuccessResponse<T> implements Response {

    @Override
    public boolean containsError() {
        return false;
    }
}
