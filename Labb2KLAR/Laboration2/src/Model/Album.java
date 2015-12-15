/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Carlos & Teddy
 * Class holds relevant information about an album such as title, name, genre 
 * ect.
 */
public class Album {    
    private SimpleStringProperty title, id, genre, artist;
    private SimpleIntegerProperty date;
    private SimpleFloatProperty rate;
   
    /**
     * Creates an album
     * @param title Album title
     * @param genre Album genre
     * @param setdate Year of release
     * @param theartist Artist who made the album
     */
    public Album(String title, String genre, int setdate, String theartist){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
        if(theartist.isEmpty())
        {
            this.artist = new SimpleStringProperty("not set yet");
        }
        else
        {
            this.artist = new SimpleStringProperty(theartist);
        }
        
    }
    
    /**
     * Creates an album
     * @param id Unique album id
     * @param title Album title
     * @param genre Album genre
     * @param setdate Year of release
     * @param theartist Artist who made the album 
     */
    public Album(String id, String title, String genre, int setdate, String theartist){
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
        if(theartist.isEmpty())
        {
            this.artist = new SimpleStringProperty("not set yet");
        }
        else
        {
            this.artist = new SimpleStringProperty(theartist);
        }
        
    }
    /**
     * 
     * @param id
     * @param title
     * @param genre
     * @param setdate
     * @param rate
     * @param theartist 
     */
    public Album(String id, String title, String genre, int setdate, float rate, String theartist){
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(rate);
        if(theartist.isEmpty())
        {
            this.artist = new SimpleStringProperty("not set yet");
        }
        else
        {
            this.artist = new SimpleStringProperty(theartist);
        }
        
    }
    /**
     * 
     * @param title
     * @param genre
     * @param setdate
     * @param setRate 
     */
    public Album(String title, String genre, int setdate, float setRate){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(setRate);
        this.artist = new SimpleStringProperty("not set yet");
    }
    /**
     * 
     * @param title
     * @param genre
     * @param setdate 
     */
    public Album(String title, String genre, int setdate){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
        this.artist = new SimpleStringProperty("not set yet");
    }
    
    
    public String getID() {
        return this.id.get();
    }
    public String getTitle() {
        return title.get();
    }
    public String getGenre(){
        return genre.get();
    }    
    public float getRate() {
        return rate.get();
    } 
    
    public int getDate() {
        return date.getValue();
    }
    
    public String getArtist() {
        return artist.get();
    }

    public void setRate(float setrate) {
        rate.set(setrate);
    }
        
    public void setArtist(String newartist)
    {
        String str = artist.get().concat(newartist + ", ") ;
        this.artist.set(str); 
    }
}