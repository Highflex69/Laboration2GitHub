/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import static java.util.concurrent.TimeUnit.SECONDS;


/**
 *
 * @author Teddy
 */
public class ConnectionMongoDB implements InterfaceMongoDB{
    private DB db;
    MongoClient mongoClient;
    public ConnectionMongoDB() throws UnknownHostException
    {
       /*
        // To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
        // if it's a member of a replica set:
        MongoClient mongoClient = new MongoClient();
        // or
        MongoClient mongoClient = new MongoClient( "localhost" );
        // or
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
                                              new ServerAddress("localhost", 27018),
                                              new ServerAddress("localhost", 27019)));*/
        mongoClient = new MongoClient( "localhost" , 27017 );

        db = mongoClient.getDB( "labb2" ); //labb2 = database               
    }
    
    //Test
    public ArrayList<Artist> getArtist(String queryStr) {
        
        ArrayList<Artist> ArtistList = new ArrayList<Artist>();
        
        if(queryStr.isEmpty())
        {
            ArtistList = getAllArtist();
        }
        else
        {
            try
            {              
                DBCollection coll = db.getCollection("artist");

                /*Scanner scan = new Scanner(System.in);
                System.out.println("Search for artist: ");
                String queryStr = scan.nextLine();*/
                
                BasicDBObject query = new BasicDBObject("name", queryStr);
                BasicDBObject doc = (BasicDBObject) coll.findOne(query);
                //test:
                ArrayList<Album> testAlbums = new ArrayList<Album>();
                testAlbums.add(new Album("testTitel","pop",2015));

                //test end
                Artist tmpartist = new Artist(doc.get("name").toString(), doc.get("nationality").toString(),testAlbums); //testAlbums för att se om kolumnen fungerar i GUI.
                System.out.println(tmpartist.getAlbumListAsString());
                System.out.println("after getAlbumList.ToString");
                ArtistList.add(tmpartist);
            }catch(NullPointerException e)
            {
                e.getMessage();
            }           
        } 
        return ArtistList; 
    }
    
    public ArrayList<Artist> getAllArtist() {
        
        ArrayList<Artist> ArtistList = new ArrayList<Artist>();
        DBCollection coll = db.getCollection("artist");
        
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Search for artist: ");
        String queryStr = scan.nextLine();*/
        
        DBCursor cursor = coll.find();
        try {
           
            while(cursor.hasNext()) {              
               System.out.println(cursor.next());
               String name = cursor.curr().get("name").toString();
               String nationality = cursor.curr().get("nationality").toString();
               ArtistList.add(new Artist(name,nationality));              
            }
        } finally {
           cursor.close();
        }       
        return ArtistList; 
    }
    
    public ArrayList<Album> getAlbum(String queryStr) {
        
        ArrayList<Album> AlbumList = new ArrayList<Album>();
        
        if(queryStr.isEmpty())
        {
            AlbumList = getAllAlbum();
        }
        else
        {
            
            DBCollection coll = db.getCollection("album");
            BasicDBObject query = new BasicDBObject("title", queryStr);
            BasicDBObject doc = (BasicDBObject) coll.findOne(query);
            //test:
            /*ArrayList<Artist> testArtist = new ArrayList<Artist>();
            testArtist.add(new Artist("testname","testnationality"));*/

            //test end
            try{
                 Album tmpalbum = new Album(doc.get("title").toString(), doc.get("genre").toString(), Integer.parseInt(doc.get("date").toString())); //testAlbums för att se om kolumnen fungerar i GUI.
                 AlbumList.add(tmpalbum);
            }catch(NullPointerException e)
            {
                e.getMessage();
            }
        }
        return AlbumList; 
    }
    
    public ArrayList<Album> getAllAlbum() {
        
        ArrayList<Album> AlbumList = new ArrayList<Album>();
        DBCollection coll = db.getCollection("album");
        
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Search for artist: ");
        String queryStr = scan.nextLine();*/
        
        DBCursor cursor = coll.find();
        try {
            while(cursor.hasNext()) {              
               System.out.println(cursor.next());
               String title = cursor.curr().get("title").toString();
               String genre = cursor.curr().get("genre").toString();
               int date = Integer.valueOf( cursor.curr().get("date").toString() );
               AlbumList.add(new Album(title,genre,date));              
            }
        } finally {
           cursor.close();
        }       
        return AlbumList; 
    }

    @Override
    public ArrayList<MadeBy> searchForString(String searchString) {
       return null; 
    }

    
    @Override
    public void addArtist(String name, String nationality) throws ArtistAlreadyExists {
        if(artistExists(name, nationality) ) {
            throw new ArtistAlreadyExists(name);
        }
        else {
            DBCollection coll = db.getCollection("artist");
            BasicDBObject artist = new BasicDBObject();
            artist.put("name", name);
            artist.put("nationality", nationality);
            coll.insert(artist);
        }

    }
    private boolean artistExists(String name, String nationality) {
        boolean exists = false;
        DBCollection coll = db.getCollection("artist");
        DBObject nameQuery = new BasicDBObject("name", name);
        long count = coll.count(nameQuery);
        try {
            if(count > 0 ) {
                exists = true;
            }
        }catch(Exception e){}
        return exists;
    }

    @Override
    public void addAlbum(String title, String genre, String artistName, int date) throws ArtistDoesNotExistException {
        
    }

    @Override
    public void rateAlbum(int userId, int albumId, int score) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

