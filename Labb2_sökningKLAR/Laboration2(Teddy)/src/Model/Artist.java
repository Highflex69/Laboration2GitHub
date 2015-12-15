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
    private SimpleStringProperty name, nationality, albumListString;
    private SimpleIntegerProperty id;
    private ArrayList<Album> albumList;
    
    public Artist(String name, String nationality)
    {
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
        
    }
    
    public Artist(String name, String nationality, ArrayList albums)
    {        
        this.name = new SimpleStringProperty(name);
        this.nationality = new SimpleStringProperty(nationality);
        this.albumList = new ArrayList<Album>(albums);
        this.albumListString = new SimpleStringProperty(getAlbumListToString());
    }
    
    public void addAlbumToArtist(Album albumtoadd)
    {
        this.albumList.add(albumtoadd);
        this.albumListString = new SimpleStringProperty(getAlbumListToString());
    }
    
    public int getID()
    {
        return 1;       //för tillfället 1, måste ta bort ID kolumnen
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
    
    private String getAlbumListToString() {
        String str = "";
        for(int i=0;i<albumList.size();i++)
        {
            str = str.concat(albumList.get(i).getTitle() + ", ");
        }
        return str;
    }
    
    public String getAlbumListAsString()
    {
        return albumListString.get();
    }
      
    public void setAlbum(Album newalbum)
    {
        albumList.add(newalbum);
    }
}
