/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Album;
import Model.Artist;
import Model.ArtistAlreadyMadeAlbum;
import Model.ArtistDoesNotExistException;
import Model.ConnectionMongoDB;
import Model.MadeBy;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Teddy
 */
public class testController  implements Initializable {

    //för Album tabellen:
    
    @FXML private TableView<Album> albumTable;
    @FXML private TableColumn idC;
    @FXML private TableColumn titleC;
    @FXML private TableColumn genreC;
    @FXML private TableColumn rateC;
    @FXML private TableColumn dateC;
    @FXML private TableColumn dateSearchC;
    @FXML private TableColumn artistC;
    
    ObservableList<Album> albumList;
    private int columnindex;
    
    @FXML private TextField titleTF;
    @FXML private TextField genreTF;
    @FXML private TextField rateTF;
    @FXML private TextField dateTF;
    @FXML private TextField madeByTF;
 
    @FXML private Button addB;
    @FXML private Button searchalbumB;
    @FXML private Button rateB;
    @FXML private Button getAlbumB;
     
    @FXML
    private void getAlbumSearch()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                ArrayList<Album> tempList = new ArrayList<>();
                ArrayList<Album> resultList = new ArrayList<>();
            
                ConnectionMongoDB con; 
                try {
                    con = new ConnectionMongoDB();
                    resultList = con.getAlbum(titleTF.getText());
                    for(Album m : resultList)
                    {
                        tempList.add(m);
                    }
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
                }

                javafx.application.Platform.runLater(new Runnable(){
                    
                    
                    public void run(){
                        if(tempList.size() > 0)
                        {
                            albumList.clear();   
                            albumList.addAll(tempList);
                            
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Album does't exist");
                            alert.show();
                        }
                        
                        
                    }
                });
            }
        }.start();  
    }
    
    @FXML
    private void getAlbumsInAdd()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                
                ArrayList<Album> tempList = new ArrayList<>();
                ArrayList<Album> resultList = new ArrayList<>();

                ConnectionMongoDB  con; 
                try {                   
                    con = new ConnectionMongoDB();
                    resultList = con.getAllAlbum();
                    tempList.addAll(resultList);
                    
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
                }
                javafx.application.Platform.runLater(new Runnable(){

                    public void run(){
                        if(tempList.size() > 0)
                        {
                            albumList.clear();
                            System.out.println(tempList.get(0).getArtist());
                            albumList.addAll(tempList);
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Album does't exist");
                            alert.show();
                        }
                        
                        
                    }
                });
            }
        }.start();  
        
    }
    
    @FXML
    private void setRateToAlbum()
    {
        
        new Thread()
        {
            @Override
            public void run()
            {            
                ArrayList<Album> resultList = new ArrayList<>();
                ConnectionMongoDB con; 
                try {
                    con = new ConnectionMongoDB();
                    resultList = con.getAlbum(titleTF.getText());
                    
                    if(resultList.size()>0)
                    {                       
                        con.rateAlbum(resultList.get(0), rateTF.getText());
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Album doesn't exist",ButtonType.OK);
                        alert.show();
                    }
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
                }
                     
            }
        }.start(); 
    }
    
    private void initiateAlbumTable()
    {
        titleC.setCellValueFactory(new PropertyValueFactory<Album,String>("Title"));
        genreC.setCellValueFactory(new PropertyValueFactory<Album,String>("Genre"));
        dateC.setCellValueFactory(new PropertyValueFactory<Album,Integer>("Date"));
        artistC.setCellValueFactory(new PropertyValueFactory<Album,String>("Artist"));
        idC.setCellValueFactory(new PropertyValueFactory<Album,Integer>("ID"));
        rateC.setCellValueFactory(new PropertyValueFactory<Album,Float>("Rate"));
        
        albumList = FXCollections.observableArrayList();
        //System.out.println(albumList.get(0).getMadeBy());
        albumTable.setItems(albumList);
    }
    
    @FXML
    private void handleAddAlbumButton(ActionEvent event) throws ArtistDoesNotExistException, UnknownHostException, ArtistAlreadyMadeAlbum
    {
       ConnectionMongoDB con;  //koppla till username och password
                try{                       
                    con = new ConnectionMongoDB();
                    
                    con.addAlbum(titleTF.getText(), genreTF.getText(), madeByTF.getText(), Integer.parseInt(dateTF.getText()));
                    System.out.println(madeByTF.getText());
                    getAlbumsInAdd();
                    con.closeMongoDB();
                    }
                catch(ArtistDoesNotExistException e){
                        Alert alertbox = new Alert(Alert.AlertType.ERROR, e.toString()+"\nPlease add the new artist.", ButtonType.OK);
                        alertbox.show();
                    }
    }
    
    //these 3 methods is from https://github.com/jarroba/Tablas-JavaFX--FXML-/blob/master/src/agendajarroba/VistaController.java and is modified after our situation.
    private final ListChangeListener<Album> focusAlbumTable = 
        new ListChangeListener<Album>() 
        {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Album> c) 
            {
                changeFocusAlbum();
            }
        };
    
     private void changeFocusAlbum() {
        final Album tempalbum = getTableOfSelectedAlbum();
        columnindex = albumList.indexOf(tempalbum);

        if (tempalbum != null) {

            // Pongo los textFields con los datos correspondientes
            titleTF.setText(tempalbum.getTitle());
            dateTF.setText(String.valueOf(tempalbum.getDate()));
            genreTF.setText(tempalbum.getGenre());
            rateTF.setText(String.valueOf(tempalbum.getRate()));
            //test:
            madeByTF.setText(tempalbum.getArtist());
            
            //end
        }
    }
     
    public Album getTableOfSelectedAlbum() {
        if (albumTable != null) {
            List<Album> table = albumTable.getSelectionModel().getSelectedItems();
            if (table.size() == 1) {
                final Album competisionselected = table.get(0);
                return competisionselected;
            }
        }
        return null;
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
        initiateAlbumTable(); 
        final ObservableList<Album> albumTableSelect = albumTable.getSelectionModel().getSelectedItems();
        albumTableSelect.addListener(focusAlbumTable); 
    }
    
}
