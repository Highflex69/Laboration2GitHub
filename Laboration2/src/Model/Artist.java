/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Teddy
 */
public class Artist {
    private SimpleStringProperty name, nationality;
    private SimpleIntegerProperty id;
    private ArrayList<Album> albumList;
    
    public Artist(int id, String name, String nationality)
    {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
    }
    
    public Artist(int id, String name, String nationality, ArrayList albums)
    {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
        this.albumList = new ArrayList<Album>(albums);
    }
    
    public int getID()
    {
        return id.get();
    }
    
    public String getName()
    {
        return name.get();
    }
    
    public String getNationality()
    {
        return nationality.get();
    }  
    
    public ArrayList getAlbumList() {
        return albumList;
    }


        
    public void setAlbum(Album newalbum)
    {
        albumList.add(newalbum);
    }
}
