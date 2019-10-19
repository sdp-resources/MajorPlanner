package authorizer;

import response.Response;
import request.Request;

public interface Authorizer {
    Response authorize(Request request);
}
