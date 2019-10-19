package interactor;

import request.Request;
import response.Response;

public interface Interactor {
    Response execute(Request request);
}
