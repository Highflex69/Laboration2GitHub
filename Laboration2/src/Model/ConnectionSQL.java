/*
 * This class handles all querries to server from client. 
 */
package Model;

/**
 *
 * @author Santos
 */
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionSQL implements InterfaceMongoDB {
    private String database, server, user, pwd;
    private Connection con;
    
    /**
     * Default constructor without parameters only for testing
     */
    public ConnectionSQL(){
        database = "labb1";
        server = "jdbc:mysql://localhost:3306/" +database;
        user = "clientapp";
        pwd = "carlos";
        
        con = null; 
    }
    
    /**
     * Sets up for connection to database named "labb1" in localhost with 
     * portnumber 3306. Username and password has to be provided.
     * @param user Username for database
     * @param pwd Password for database
     */
    public ConnectionSQL(String user, String pwd) {
        database = "labb1";
        server = "jdbc:mysql://localhost:3306/" + database;
        this.user = user;
        this.pwd = pwd;
        
        con = null;
    }
    /**
     * Takes a string as parameter and searches database for match with artist 
     * name or album title. Returns a list containing all matches.
     * @param searchString String that is matched with database
     * @return ArrayList<MadeBy> containing result of search.
     */
    @Override
    public ArrayList<MadeBy> searchForString(String searchString){
      ArrayList<MadeBy> resultMadeByList = new ArrayList<>();
      int artistId, albumId;
      
      try{
          Class.forName("com.mysql.jdbc.Driver");
          
          con = DriverManager.getConnection(server, user, pwd);
          String sql = "";
          if(searchString.isEmpty())
          {
              sql = "select * from t_madeby";
          }
          else
          {
             sql =  "select K_ArtistId, K_AlbumId " +
                    "from T_MadeBy " +
                    "inner join T_Album on T_MadeBy.K_AlbumId = T_Album.K_Id " +
                    "inner join T_Artist on T_MadeBy.K_ArtistId = T_Artist.K_id " +
                    "where T_Artist.K_Name like '%"+searchString+"%' " +
                    "or T_Album.K_Title like '%"+searchString+"%' " +
                    "or T_Album.K_Genre like '%"+searchString+"%'"; 
          }          
          PreparedStatement searchDatabase = con.prepareStatement(sql);
          
          ResultSet rs = searchDatabase.executeQuery();
          
          while(rs.next()) {
              artistId = rs.getInt(1);
              Artist artistTmp = getArtistById(con, artistId);
              albumId = rs.getInt(2);
              Album albumTmp = getAlbumById(con, albumId);              
              resultMadeByList.add(new MadeBy(artistTmp, albumTmp));

          }          
         
          return resultMadeByList;
          
          
      }catch(Exception e) {System.out.println("Error when searching");}      
      finally
      {
          try
          {
              con.close();
          }
          catch(SQLException e){}
      }
      return resultMadeByList;
    }
    
    //For testing
    private void testList(ArrayList<MadeBy> resultMadeByList) {
        for(MadeBy m: resultMadeByList) {
            System.out.println(m.getArist().getName() +m.getAlbum().getTitle());
        }
    }
    
    private Artist getArtistById(Connection con, int id) {
        Artist resultArtist = null;
        
        try {
            String sql = "select * from T_Artist where K_Id = ?";
            PreparedStatement getArtist = con.prepareStatement(sql);
            getArtist.setInt(1, id);
            ResultSet rs = getArtist.executeQuery();
            
            if(rs.next() )  {
                resultArtist = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
                
        }catch(Exception e) { }
        
        return resultArtist;
    }
    private Album getAlbumById(Connection con, int id) {
        Album resultAlbum = null;
        
        try {
            String sql = "select * from T_Album where K_Id = ?";
            PreparedStatement getArtist = con.prepareStatement(sql);
            getArtist.setInt(1, id);
            ResultSet rs = getArtist.executeQuery();
            
            if(rs.next() )  {
                resultAlbum = new Album(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5));
            }
                
        }catch(Exception e) { }
        
        return resultAlbum;
    }
    
    /**
     * Returns ArrayList with artists in database that matches search string.
     * @param searchString Search string to be searched by.
     * @return ArrayList<Artist> 
     */
    public ArrayList<Artist> searchForArtistByName(String searchString) {
        ArrayList<Artist> artistList = new ArrayList<>();
        try {
            
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(server, user, pwd);
            
            Statement st = con.createStatement();
            String sql = "Select * from T_Artist where K_Name = " +"\"" +searchString +"\";";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next() ) {
                artistList.add(new Artist(rs.getInt(1), rs.getString(2), rs.getString(3) ));
            }

        }
        catch(Exception e) {System.out.println("Connection lost");}
        finally {
            try {
                con.close();
            }catch(SQLException e) {}
        }
        return artistList;
    }
    
    /**
     * Add artist to database
     * @param name Artist name
     * @param nationality Artist Nationality
     */
    @Override
    public void addArtist(String name, String nationality) {
        
                
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);
            String sql = "Insert into T_Artist(K_Name, K_Nationality) values(?,?)";
            
            PreparedStatement addArtist = con.prepareStatement(sql);
            addArtist.setString(1, name);
            addArtist.setString(2, nationality);            
            
            int n = addArtist.executeUpdate();
            
        }catch(Exception e) {System.out.println("Connection lost");}
        finally {
            try{
                con.close();
            }catch(SQLException e){}
        }
    }
    
    

    
            
    /**
     * Adds album to database based on parameters. Checks if artist and album 
     * already exists before and adding the album. 
     * @param title Title of album
     * @param genre Genre of album
     * @param artistName Artist that made the album
     * @param date Date of release
     * @throws ArtistDoesNotExistException If artist doesn´t exist already in database
     *         
     */
    @Override
    public void addAlbum(String title, String genre, String artistName, int date) throws ArtistDoesNotExistException {
        
        if(!checkIfArtistExists(artistName) ) {
            throw new ArtistDoesNotExistException(artistName);
        }
        else {
            if(checkIfAlbumExists(title, artistName) ) {
                //Bör göra exception
            }
            else {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    con = DriverManager.getConnection(server, user, pwd);                    
                    con.setAutoCommit(false);
                    
                    System.out.println("Getting artist id");//Endast för test
                    //Get Artist Id that matches artistName
                    String sqlGetId = "select K_Id from T_Artist where K_Name = ?";
                    PreparedStatement getArtistId = con.prepareStatement(sqlGetId);
                    getArtistId.setString(1, artistName);
                    ResultSet rs = getArtistId.executeQuery();
                    boolean next = rs.next();
                    int artistId = rs.getInt(1);
                    
                    System.out.println("Inserting row for album");//Endast för test
                    //Insert row for album in T_Album
                    String sqlInsertAlbum = "insert into T_Album(K_Title, K_Genre, K_Date) "
                                +"values(?, ?, ?)";
                    PreparedStatement insertAlbum = con.prepareStatement(sqlInsertAlbum);
                    insertAlbum.setString(1, title);
                    insertAlbum.setString(2, genre);
                    insertAlbum.setInt(3, date);
                    int n = insertAlbum.executeUpdate();
                    
                    //Fungerar inte
                    System.out.println("Inserting album in madeBy");//Endast för test
                    //Insert row in T_MadeBy for album created and artist defined in artistName
                    String sqlInsertMadeBy = "insert into T_MadeBy(K_ArtistId, K_AlbumId) "
                                            +"values( (select K_Id from T_Artist where K_Name = ?), "
                                            +"(select K_Id from T_Album where K_Title = ?) )";
                    PreparedStatement insertMadeBy = con.prepareStatement(sqlInsertMadeBy);
                    insertMadeBy.setString(1, artistName);
                    insertMadeBy.setString(2, title);
                    insertMadeBy.executeUpdate();
                    System.out.println("Inserting album in madeBy done");//Endast för test
                    
                    con.commit();                    
                    con.setAutoCommit(true);

                }catch(Exception e) {System.out.println("Exception");}
                    
                finally {
                    try {
                        con.rollback();
                        con.close();
                    }catch(SQLException e) {}
                }
            }
        
        }
    }
    
    private boolean checkIfArtistExists(String artistName) {
        
        boolean artistFound = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);    
            
            String sql = "select count(K_Id) from T_Artist where K_Name = ?";
            PreparedStatement checkArtistExistance = con.prepareStatement(sql);
            checkArtistExistance.setString(1, artistName);      
            
            ResultSet rs = checkArtistExistance.executeQuery();
            
            boolean next = rs.next();
            if(rs.getInt(1) > 0) {
                artistFound = true;
            }
            
        }catch(Exception e) {}
        finally {
            try {
                con.close();
            }catch(SQLException e) {}
        }
        
        return artistFound;
    }
    
    private boolean checkIfAlbumExists(String title, String artistName) {
        boolean albumFound = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);    
            
            String sql = "select count(K_ArtistId) from T_MadeBy where K_Name = ? AND A_Id = "
                        +"(select K_Id from T_Album where K_Title = ?";
            PreparedStatement checkArtistExistance = con.prepareStatement(sql);
            checkArtistExistance.setString(1, artistName);      
            checkArtistExistance.setString(1, title);  
            
            ResultSet rs = checkArtistExistance.executeQuery();
            System.out.println("ASdf");
            boolean next = rs.next();
            if(rs.getInt(1) > 0) {
                albumFound = true;
            }
            
        }catch(Exception e) {}
        finally {
            try {
                con.close();
            }catch(SQLException e) {}
        }
        return albumFound;
    }
    
    /**
     * Rates an album. 
     * @param userId User id that wants to make a rating
     * @param albumId The album id tobe rated
     * @param score User score for the album
     */
    @Override
    public void rateAlbum(int userId, int albumId, int score) {
        
        try{
            String sql;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);
            if(alreadyRated(con, userId, albumId) )
            {
                sql = "update T_Rate set k_Score = ? where K_User = ? and K_Album = ?";
            }
            else
            {
                sql = "insert into T_Rate(K_Score, K_User, K_Album) values(?, ?, ?)";
            }
            
            
            PreparedStatement insertScoreSt = con.prepareStatement(sql);
  
            insertScoreSt.setInt(1, score);
            insertScoreSt.setInt(2, userId);
            insertScoreSt.setInt(3, albumId);
            
            int n = insertScoreSt.executeUpdate();

        }catch(Exception e) {}
        finally {
            try {
                con.rollback();
                con.close();
            }catch(SQLException e) {}
        } 
    }
    
    
    
    private boolean alreadyRated(Connection con, int userId, int albumId) {
        boolean alreadyRated = false;
        

        String sql = "Select count(K_User) from T_Rate where K_User = ? and K_Album = ?";
        
        try{
            
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, albumId);
            ResultSet rs = st.executeQuery();
            rs.next();
            
            int n = rs.getInt(1);            
            if(n > 0)
            {
                
                alreadyRated = true;
            }
            
        }catch(Exception e){}

        return alreadyRated;
    }
    
    /**
     * Closes Connection to database. Should be closed before exiting application.
     */
    public void closeConnection() {
        try{
            con.close();
        }catch(Exception e) {}
    }
    
    
}
