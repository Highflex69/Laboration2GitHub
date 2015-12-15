/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Not used in mongo databse.
 * @author Carlos & Teddy
 */
public class MadeBy {
    private Artist artist;
    private Album album;
    
    public MadeBy(Artist artist, Album album) {
        this.artist = artist;
        this.album = album;
    }
    
    public Artist getArist() {
        return artist;
    }
    
    public Album getAlbum() {
        return album;
    }
    
}
