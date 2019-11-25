package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.request.ViewCourseListRequest;
import majorPlanner.response.Response;

import java.util.ArrayList;

public class ViewCourseListInteractor implements Interactor {
    public ViewCourseListInteractor(ViewCourseListRequest request) {
    }

    @Override
    public Response execute() {
        // TODO: Implement correctly
        return Response.success(new ArrayList<Course>());
    }
}
