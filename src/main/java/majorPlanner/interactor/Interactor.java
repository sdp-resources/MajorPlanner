package majorPlanner.interactor;

import majorPlanner.request.Request;
import majorPlanner.response.Response;

public interface Interactor {
    Response execute(Request request);
}
