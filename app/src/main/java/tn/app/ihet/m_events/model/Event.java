package tn.app.ihet.m_events.model;

import android.graphics.drawable.Drawable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {

    @PrimaryKey
    private int id ;
    private String name;
    private String description;
    private String date_debut;
    private String date_fin;
    private Double prix;
    private int image;
    private String imageString;
    private Double rating;



    public Event()
    {
    }

    public Event(int id, String name, String description, String date_debut, String date_fin, Double prix, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix = prix;
        this.image = image;
    }

    public Event(int id, String name, String description, String date_debut, String date_fin, Double prix, int image, Double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix = prix;
        this.image = image;
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", prix=" + prix +
                ", image=" + image +
                ", imageString='" + imageString + '\'' +
                ", rating=" + rating +
                '}';
    }
}
