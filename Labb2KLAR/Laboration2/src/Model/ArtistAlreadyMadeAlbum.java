/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Exception to be called when trying to add album that already exists in 
 * database with the specific artist as maker.
 * @author Carlos & Teddy
 */
public class ArtistAlreadyMadeAlbum extends Exception{
    private String title, genre, artist;
    private int date;
    
    public ArtistAlreadyMadeAlbum(String title, String genre, int date, String artist) {
        this.artist = artist;
        this.title = title;
        this.genre = genre;
        this.date = date;
    }
    
    public String toString() {
        return "The album \"" +title +"\"" +"Genre:" +genre +" Made:" +date +" already exists.";
    }
}