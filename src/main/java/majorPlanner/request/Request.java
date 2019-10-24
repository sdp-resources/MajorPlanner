package majorPlanner.request;

public interface Request {
    <T> T accept(RequestVisitor<T> visitor);
}
