/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Album;
import Model.Artist;
import Model.ArtistAlreadyExists;
import Model.ArtistDoesNotExistException;
import Model.ConnectionMongoDB;
import java.net.UnknownHostException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 *
 * @author Teddy
 */ 
public class DatabaseUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       //showMenu();
        ConnectionMongoDB db = new ConnectionMongoDB();
        
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogIn.fxml"));        
        Scene scene = new Scene(root);       
        stage.setScene(scene);
        stage.show();
    }
    
    private void showMenu() throws UnknownHostException, ArtistDoesNotExistException, ArtistAlreadyExists
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Menu:");
        System.out.println("1. Add ");
        System.out.println("2. search ");
        
        switch(scan.nextLine())
        {
            case "1":
                addMenu();
                break;
            case "2":
                searchMenu();
                break;
            default:
                
        }  
    }
    
    private void addMenu() throws ArtistDoesNotExistException, UnknownHostException, ArtistAlreadyExists
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Menu-Add:");
        System.out.println("1. Add Artist ");
        System.out.println("2. Add Album ");
        
        switch(scan.nextLine())
        {
            case "1":
                addArtistMenu();
                break;
            case "2":
                addAlbumMenu();
                break;
            default:
                
        }  
    }
    
    private void addArtistMenu() throws UnknownHostException, ArtistAlreadyExists
    {
        Scanner scan = new Scanner(System.in);
        String name, nationality, answer;
        
        System.out.println("Menu-Add-Add_Artist:");
        System.out.println("Name: ");
        name = scan.next();
        System.out.println("Nationality: ");
        nationality = scan.next();
        
        Artist newartist = new Artist(name, nationality);
        System.out.println("Do you want to insert albums to this artist?");
        System.out.println("1.Yes, I want to insert a new album to this artist");
        System.out.println("2. Yes, I want to insert a existing album to this artist");
        System.out.println("3. No");
        answer = scan.next();
        switch(answer)
        {
            case "1":
                System.out.println("cannot do this right now");
                //newartist.setAlbum();
                break;
            case "2":
                System.out.println("cannot do this right now");
                //newartist.setAlbum();
                break;
            case "3":
                System.out.println("ok, this artist has been added");
            default:              
        }
        ConnectionMongoDB con = new ConnectionMongoDB();
        con.addArtist(newartist.getName(), newartist.getNationality());     
    }
    
    private void addAlbumMenu() throws UnknownHostException, ArtistDoesNotExistException
    {
        Scanner scan = new Scanner(System.in);
        String title, genre, rate, madeby, answer;
        int date;
        
        System.out.println("Menu-Add-Add_Artist:");
        System.out.println("title: ");
        title = scan.next();
        System.out.println("genre: ");
        genre = scan.next();
        System.out.println("rate: ");
        rate = scan.next();
        System.out.println("date: ");
        date = scan.nextInt();
        System.out.println("made by(Artist): ");
        madeby = scan.next();
        
        Album newalbum = new Album(title,genre,date);

        ConnectionMongoDB con = new ConnectionMongoDB();
        //con.addAlbum(newalbum.getTitle(), newalbum.getGenre(), newalbum.getArtist(), newalbum.getDate());     
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   

    private void searchMenu() {
        System.out.println("Empty at the moment");
    }
}
