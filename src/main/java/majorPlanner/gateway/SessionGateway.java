package majorPlanner.gateway;

import majorPlanner.session.Session;

public interface SessionGateway {
    void addSession(Session session);
    Session getSession(String token);
}
