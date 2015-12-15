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
public class test2Controller  implements Initializable {

    // för Artist tabellen;
    @FXML private TableView<Artist> artistTable;
    @FXML private TableColumn artistidC;
    @FXML private TableColumn nameC;
    @FXML private TableColumn nationalityC;
    @FXML private TableColumn albumsC;
  
    ObservableList<Artist> artistList;
    private int searchsolumnindex;
    
    @FXML private TextField artistnameTF;
    @FXML private TextField artistnationalityTF;
    
    @FXML private Button searchartistB;
    @FXML private Button searchalbumB;
    @FXML private Button returntomenuB;
    @FXML private Button getAlbumListB;
    @FXML private Button addArtistB;
    
    //för Album tabellen:
    
    private void initiateArtistTable()
    {
        artistidC.setCellValueFactory(new PropertyValueFactory<Artist,String>("ID"));
        nameC.setCellValueFactory(new PropertyValueFactory<Artist,String>("Name"));
        nationalityC.setCellValueFactory(new PropertyValueFactory<Artist,String>("Nationality"));
        albumsC.setCellValueFactory(new PropertyValueFactory<Artist,ArrayList<Album>>("AlbumList"));
        
        artistList = FXCollections.observableArrayList();
        artistTable.setItems(artistList);
        
        System.out.println(artistList.toString());
    }
    
    @FXML
    public void getAllArtist() throws UnknownHostException  //gives list of artist from the database
    {
        
        new Thread()
        {
            
            @Override
            public void run()
            {
               
                ArrayList<Artist> tempList = new ArrayList<>();
                ArrayList<Artist> resultList = new ArrayList<>();
                ConnectionMongoDB con;     
                try {
                    con = new ConnectionMongoDB();
                    resultList = con.getAllArtist();
                    for(Artist a : resultList)
                    {
                        tempList.add(a);
                    }
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(test2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                javafx.application.Platform.runLater(new Runnable(){
                    public void run(){
                        artistList.clear();   
                        artistList.addAll(tempList);
                    }
                });
            }
        }.start();   
    }
    
    @FXML
    private void addArtist()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                ConnectionMongoDB con; 
                try {
                    con = new ConnectionMongoDB(); //koppla till username och password
                    try {
                        con.addArtist(artistnameTF.getText(), artistnationalityTF.getText());
                        System.out.println(artistnameTF.getText() +" " + artistnationalityTF.getText());
                    } catch (ArtistAlreadyExists ex) {
                        Logger.getLogger(test2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(test2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();  
    }
    
    @FXML
    private void getSearchTest() 
    {
        new Thread()
        {
            @Override
            public void run()
            {
                ArrayList<Artist> resultList = new ArrayList<>();
                ArrayList<Artist> tempList = new ArrayList<>();
                try {
                    ConnectionMongoDB con = new ConnectionMongoDB(); 
                    resultList = con.getArtist(artistnameTF.getText());
                    for(Artist m : resultList)
                    {
                        tempList.add(m);
                    }
                    con.closeMongoDB();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(test2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                javafx.application.Platform.runLater(new Runnable(){
                    
                    
                    public void run(){
                        if(tempList.size() > 0)
                        {
                            artistList.clear();   
                            artistList.addAll(tempList);
                            
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Artist does't exist or have a album");
                            alert.show();
                        }
                        
                        
                    }
                });
            }
        }.start(); 
        
    }
    //focus pull-in
    private final ListChangeListener<Artist> focusArtistTable = 
        new ListChangeListener<Artist>() 
        {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Artist> c) 
            {
                changeFocusArtist();
            }
        };
    
     private void changeFocusArtist() {
        final Artist tempartist = getTableOfSelectedArtist();
        searchsolumnindex = artistList.indexOf(tempartist);

        if (tempartist != null) {
            
            artistnameTF.setText(tempartist.getName());
            artistnationalityTF.setText(tempartist.getNationality());
        }
    }
     
    public Artist getTableOfSelectedArtist() {
        if (artistTable != null) {
            List<Artist> table = artistTable.getSelectionModel().getSelectedItems();
            if (table.size() == 1) {
                final Artist competisionselected = table.get(0);
                return competisionselected;
            }
        }
        return null;
    }
    // end
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
       
        initiateArtistTable();
        final ObservableList<Artist> artistTableSelect = artistTable.getSelectionModel().getSelectedItems();
        artistTableSelect.addListener(focusArtistTable);
    }
    
}

