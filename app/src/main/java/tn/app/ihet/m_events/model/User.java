package tn.app.ihet.m_events.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String username;
    private String email;
    private String urlImage;

    public User()
    {

    }

    public User(int id, String name, String username, String email, String urlImage) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.urlImage = urlImage;
    }

    public User(String name, String username, String email, String urlImage) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
