package tn.app.ihet.m_events.db;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import tn.app.ihet.m_events.interfaces.UserCallback;
import tn.app.ihet.m_events.model.User;

public class UserManager {

    private Context context;
    private Realm realm = null;


    public UserManager(Context context) {
        this.context = context;
    }

    public void addUser(final User user) {

        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@SuppressWarnings("NullableProblems") Realm realm) {

                    try {
                        Log.e("user", "execute: " + user);

                        realm.insertOrUpdate(user);

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

    public void getUser(final UserCallback userCallback) {


        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@SuppressWarnings("NullableProblems") Realm realm) {


                User user = realm.where(User.class).findFirst();
                if (user != null) {
                    userCallback.setUser(user);
                    Log.e("image", "execute: " + user.getUrlImage() );
                }
                else
                    userCallback.setError("Utilisateur introuvable ! ");
                Log.e("User", "execute: " + user);


            }
        });


    }

    public  void deleteUser()
    {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void execute(Realm realm) {
                RealmResults<User> rows = realm.where(User.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }
}

