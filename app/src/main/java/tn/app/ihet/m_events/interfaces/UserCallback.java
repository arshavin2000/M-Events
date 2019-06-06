package tn.app.ihet.m_events.interfaces;

import tn.app.ihet.m_events.model.User;

public interface UserCallback {

    void setUser(User user);
    void setError(String msg);
}
