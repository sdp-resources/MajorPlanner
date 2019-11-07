package majorPlanner.authorizer;

import majorPlanner.request.Request;
import majorPlanner.response.Response;

public interface Authorizer {
    Response authorize(Request request);
}
