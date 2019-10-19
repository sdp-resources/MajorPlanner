package mp.interactor;

import mp.request.Request;
import mp.response.Response;

public interface Interactor {
    Response execute(Request request);
}
