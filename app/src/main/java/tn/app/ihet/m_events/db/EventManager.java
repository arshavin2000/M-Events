package tn.app.ihet.m_events.db;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import tn.app.ihet.m_events.interfaces.EventCallback;
import tn.app.ihet.m_events.model.Event;

public class EventManager {

    private Context context;
    private Realm realm = null;


    public EventManager(Context context) {
        this.context = context;
    }

    public void addEvent(final Event event) {

        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@SuppressWarnings("NullableProblems") Realm realm) {

                    try {
                        Log.e("user", "execute: " + event);

                        realm.insertOrUpdate(event);

                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(context, "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public void getEvent(final EventCallback eventCallback) {


        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@SuppressWarnings("NullableProblems") Realm realm) {


                Event event = realm.where(Event.class).findFirst();
                if (event != null) {
                    eventCallback.setEvent(event);
                    Log.e("image", "execute: " + event.getName() );
                }
                else
                    eventCallback.setError("Événement introuvable ! ");
                Log.e("User", "execute: " + event);


            }
        });


    }

    public void deleteEvent(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Event result = realm.where(Event.class).equalTo("id", id).findFirst();
        assert result != null;
        result.deleteFromRealm();
        realm.commitTransaction();

    }

    public List<Event> getAllEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Event.class);
        return realm.copyFromRealm(query.findAll());

    }
}
