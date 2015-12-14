/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Album;
import Model.Artist;
import Model.ArtistDoesNotExistException;
import Model.ConnectionSQL;
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
                ArrayList<MadeBy> resultList = new ArrayList<>();
            
                ConnectionSQL con = new ConnectionSQL("clientapp", "qwerty"); //koppla till username och password

                resultList = con.searchForString(titleTF.getText());
                for(MadeBy m : resultList)
                {
                    tempList.add(m.getAlbum());
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
                ArrayList<MadeBy> resultList = new ArrayList<>();

                ConnectionSQL con = new ConnectionSQL("clientapp", "qwerty"); //koppla till username och password        
                resultList = con.searchForString("");
                
                for(MadeBy m : resultList)
                {
                    tempList.add(m.getAlbum());
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
    private void setRateToAlbum()
    {
        
        new Thread()
        {
            @Override
            public void run()
            {            
                ArrayList<MadeBy> resultList = new ArrayList<>();
                ConnectionSQL con = new ConnectionSQL("clientapp", "qwerty"); //koppla till username och password
                resultList = con.searchForString(titleTF.getText());
                System.out.println("albumid:"+resultList.get(0).getAlbum().getID() + "\nrate: "+rateTF.getText());
                if(resultList.size()>0)
                {
                    con.rateAlbum(1, resultList.get(0).getAlbum().getID(), Integer.parseInt(rateTF.getText()) );
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Album doesn't exist",ButtonType.OK);
                    alert.show();
                }       
            }
        }.start();    
    }
    
    private void initiateAlbumTable()
    {
        titleC.setCellValueFactory(new PropertyValueFactory<Album,String>("Title"));
        genreC.setCellValueFactory(new PropertyValueFactory<Album,String>("Genre"));
        dateC.setCellValueFactory(new PropertyValueFactory<Album,String>("Date"));
        //dateSearchC.setCellValueFactory(new PropertyValueFactory<Album,String>("Date"));
        idC.setCellValueFactory(new PropertyValueFactory<Album,String>("ID"));
        rateC.setCellValueFactory(new PropertyValueFactory<Album,String>("Rate"));
        
        albumList = FXCollections.observableArrayList();
        albumTable.setItems(albumList);
    }
    
    @FXML
    private void handleAddAlbumButton(ActionEvent event) throws ArtistDoesNotExistException
    {
        ConnectionSQL con = new ConnectionSQL("clientapp", "qwerty"); //koppla till username och password
                try{                       
                        con.addAlbum(titleTF.getText(), genreTF.getText(), madeByTF.getText(), Integer.parseInt(dateTF.getText()) );
                        System.out.println(madeByTF.getText());
                        getAlbumsInAdd();
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
            madeByTF.setText(tempalbum.GetMadeBy());
            
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
