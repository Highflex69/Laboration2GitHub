/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Album;
import Model.Artist;
import Model.ArtistDoesNotExistException;
import Model.MadeBy;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Teddy
 */
public class FXMLController implements Initializable{
    private String username;
    private String password;
    private String artist;
    private String album;
    private String title;
    private String genre;
    private String madeby;
    private String nationality;
    private int date;
    
    @FXML private TextField usernameTF;
    @FXML private TextField passwordTF;
    
       
   @FXML
    private Label label;
    @FXML
    private Button returnB;
   
    //Login scene:
    @FXML
    private void handleConnectButtonOnClicked(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");

            //här kopplas det till servern med username och password           
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLsceneParent = FXMLLoader.load(getClass().getResource("FXMLSQL_scene.fxml"));
            Scene SQLsceneScene = new Scene(SQLsceneParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLsceneScene);
            mainStage.show();
          
    }
    
    @FXML
    private void handleLogInUsernameEvent(ActionEvent event)
    {
        
        TextField text = (TextField) event.getSource();
        username = text.getText();
        System.out.println(username);
    }
    
    @FXML
    private void handleLogInPasswordEvent(ActionEvent event)
    {
        PasswordField psw = (PasswordField) event.getSource();
        password = psw.getText();
        System.out.println(password);
    }
    
    //SQL_scene:
    @FXML
    private void handleSQLquestionSearchButtonEvent(ActionEvent event) throws IOException
    {
        Parent SQLSearchParent = FXMLLoader.load(getClass().getResource("FXMLSearch.fxml"));
        Scene SQLSearchScene = new Scene(SQLSearchParent);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(SQLSearchScene);
        mainStage.show();
    }
    
    @FXML
    private void handleSQLquestionAddButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLAddPage.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    @FXML
    private void handleAddPageArtistButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLAddArtist.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    @FXML
    private void handleAddPageAlbumButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLResultAdd.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    
    @FXML
    private void handleSQLquestionRateButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLRateParent = FXMLLoader.load(getClass().getResource("FXMLRatePage.fxml"));
            Scene SQLRateScene = new Scene(SQLRateParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLRateScene);
            mainStage.show();
    }
    
    
    /*
        rate object scene:
    */
     @FXML
    private void handleRateSearchAlbumButtonEvent(ActionEvent event) throws IOException
    {
        if(album.isEmpty())
        {
            System.out.println("artist is empty!");
        }
        else
        {
            Parent SQLRateParent = FXMLLoader.load(getClass().getResource("FXMLRatePage.fxml"));
            Scene SQLRateScene = new Scene(SQLRateParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLRateScene);
            
            System.out.println("Searching...");
        }
    }
    
    @FXML 
    private void handleRateSearchAlbumTextFieldEvent(ActionEvent event)
    {
        TextField text = (TextField) event.getSource();
        album = text.getText();
        System.out.println(album);
    }
    
    @FXML 
    private void handleRateAlbumEvent(ActionEvent event)
    {
        
    }
    /*
        Search scene:
    */
    
    @FXML
    private void handleSearchAButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLSearchArtist.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    
    @FXML
    private void handleSearchBButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLSearchAlbum.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    
    
    @FXML
    private void handleReturnButtonEvent(ActionEvent event) throws IOException
    {
            //här hämtas root och stage som vi sedan endast byter scener.
            Parent SQLAddParent = FXMLLoader.load(getClass().getResource("FXMLSQL_scene.fxml"));
            Scene SQLAddScene = new Scene(SQLAddParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(SQLAddScene);
            mainStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}