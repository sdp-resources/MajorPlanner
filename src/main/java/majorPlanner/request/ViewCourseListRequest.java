package majorPlanner.request;

public class ViewCourseListRequest extends Request {
    private int requirementId;

    public ViewCourseListRequest(int requirementId) {
        this.requirementId = requirementId;
    }

    @Override
    public <T> T accept(RequestVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public int getRequirementId() {
        return requirementId;
    }
}
