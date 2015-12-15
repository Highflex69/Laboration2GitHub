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
    private SimpleStringProperty id, name, nationality, albumList;
    //private SimpleIntegerProperty id;
    
    public Artist(String name, String nationality)
    {
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
        this.albumList = new SimpleStringProperty("not set yet");
    }
    
    public Artist(String id,String name, String nationality, String albums)
    {        
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
        if(albums.isEmpty())
        {
            this.albumList = new SimpleStringProperty("not set yet");
        }
        else
        {
            this.albumList = new SimpleStringProperty(albums);
        }
        
    }
    
    public void addAlbumToArtist(String albumtoadd)
    {
        String str = this.albumList.get().concat(albumtoadd + ", ");
        this.albumList.set(str);
    }
    
    public String getID()
    {
        return id.get();       //för tillfället 1, måste ta bort ID kolumnen
    }
    
    public String getName()
    {
        return name.get();
    }
    
    public String getNationality()
    {
        return nationality.get();
    }  
    
    public String getAlbumList() {
        return albumList.get();
    }

}
