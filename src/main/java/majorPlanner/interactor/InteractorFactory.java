package majorPlanner.interactor;

import majorPlanner.request.Request;

public interface InteractorFactory {
    Interactor getInteractorFor(Request request);
}
