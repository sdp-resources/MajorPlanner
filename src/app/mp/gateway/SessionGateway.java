package mp.gateway;

import mp.session.Session;

public interface SessionGateway {
    void addSession(Session session);
    Session getSession(String token);
}
