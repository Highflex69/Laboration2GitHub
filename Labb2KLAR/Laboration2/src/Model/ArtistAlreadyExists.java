/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Exception to be called when trying to add artist that already exists in 
 * database
 * @author Carlos & Teddy
 */
public class ArtistAlreadyExists extends Exception{
    private String artist;
    
    public ArtistAlreadyExists(String artist) {
        this.artist = artist;
    }
    
    public String toString() {
        return "The artist " +artist +" already exists.";
    }
}
