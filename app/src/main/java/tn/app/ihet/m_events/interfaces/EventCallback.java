package tn.app.ihet.m_events.interfaces;


import tn.app.ihet.m_events.model.Event;

public interface EventCallback {

    void setEvent(Event event);
    void setError(String msg);
}
