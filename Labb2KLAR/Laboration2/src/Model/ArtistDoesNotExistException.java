/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Exception to be called when trying to add album with an artist asociated that
 * does not exist in database.
 * @author Carlos & Teddy
 */
public class ArtistDoesNotExistException extends Exception{
    private String artist;
    
    public ArtistDoesNotExistException(String artist) {
        this.artist = artist;
    }
    
    public String toString() {
        return "The artist " +artist +" does not exist.";
    }
}
