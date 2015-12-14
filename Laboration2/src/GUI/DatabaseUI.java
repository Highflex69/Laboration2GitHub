/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.ConnectionMongoDB;
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
       ConnectionMongoDB mongodb = new ConnectionMongoDB();
         
        System.out.println(mongodb.getCollection("album"));
        /*Parent root = FXMLLoader.load(getClass().getResource("FXMLLogIn.fxml"));        
        Scene scene = new Scene(root);       
        stage.setScene(scene);
        stage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
