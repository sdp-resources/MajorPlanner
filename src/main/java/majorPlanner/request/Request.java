package majorPlanner.request;

import majorPlanner.session.Session;

public abstract class Request {
    private Session session;

    public abstract <T> T accept(RequestVisitor<T> visitor);

    public Session getSession(){
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }
}
