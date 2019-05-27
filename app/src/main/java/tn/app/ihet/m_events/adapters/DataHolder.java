package tn.app.ihet.m_events.adapters;

import tn.app.ihet.m_events.model.Event;

public class DataHolder {
    //design pattern to share arguments between fragments and activities
    private static DataHolder dataHolder = null;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        if (dataHolder == null)
            dataHolder = new DataHolder();
        return dataHolder;
    }


    private Event event ;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
