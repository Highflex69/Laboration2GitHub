/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Carlos & Teddy
 */
public interface InterfaceMongoDB {
    
    //Searches database for results that matches searchString
    public ArrayList<MadeBy> searchForString(String searchString);
    
    //Adds artist to database
    public void addArtist(String name, String nationality)throws ArtistAlreadyExists;
    
    //Adds album to database
    public void addAlbum(String title, String genre, String artistName, int date) 
            throws ArtistDoesNotExistException, ArtistAlreadyMadeAlbum;
    
    //Rates specific album, userId provided to database to prevent multiple rates
    //of a album by same user
    public void rateAlbum(Album album, String score);
}
