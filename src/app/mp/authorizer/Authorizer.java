package mp.authorizer;

import mp.response.Response;
import mp.request.Request;

public interface Authorizer {
    Response authorize(Request request);
}
