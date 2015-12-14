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
 * @author Santos
 */
public class Album {    
    private SimpleStringProperty title, genre;
    private SimpleIntegerProperty id, date;
    private SimpleFloatProperty rate;
    private ArrayList<Artist> madeby;
   
    
    public Album(int id, String title, String genre, int setdate, ArrayList artistList){
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
        this.madeby = new ArrayList<Artist>(artistList);
    }
    
    public Album(int id, String title, String genre, int setdate, float setRate){
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(setRate);
    }
    
    
    public int getID() {
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
        return date.get();
    }
    
    public ArrayList getMadeBy() {
        return madeby;
    }
    
    public String getMadeByToString()
    {
        return madeby.toString();
    }

    public void setRate(float setrate) {
        rate.set(setrate);
    }
        
    public void setMadeBy(Artist newartist)
    {
        madeby.add(newartist);
    }
}