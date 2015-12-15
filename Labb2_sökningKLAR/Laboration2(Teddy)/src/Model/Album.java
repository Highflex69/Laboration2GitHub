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
   
    
    public Album(String title, String genre, int setdate, ArrayList artistList){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
        this.madeby = new ArrayList<Artist>(artistList);
    }
    
    public Album(String title, String genre, int setdate, float setRate){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(setRate);
    }
    
    public Album(String title, String genre, int setdate){
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleIntegerProperty(setdate);
        this.rate = new SimpleFloatProperty(0);
    }
    
    
    public int getID() {
        return 1/*this.id.get()*/;
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
        String str = "";
        for(int i=0;i<madeby.size();i++)
        {
            str = str.concat(madeby.get(i).getName()+ ", ");
        }
        return str;
    }

    public void setRate(float setrate) {
        rate.set(setrate);
    }
        
    public void setMadeBy(Artist newartist)
    {
        madeby.add(newartist);
    }
}