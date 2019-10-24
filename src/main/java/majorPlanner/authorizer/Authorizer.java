package majorPlanner.authorizer;

import majorPlanner.request.RequestVisitor;
import majorPlanner.response.Response;
import majorPlanner.request.Request;

public interface Authorizer {
    Response authorize(Request request);
}
