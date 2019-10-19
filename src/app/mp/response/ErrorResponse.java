package mp.response;

public class ErrorResponse implements Response {
    private final String error;

    public ErrorResponse(String error)
    {
        this.error = error;
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
