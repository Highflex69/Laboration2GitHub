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
import javafx.stage.WindowEvent;

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
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
}
