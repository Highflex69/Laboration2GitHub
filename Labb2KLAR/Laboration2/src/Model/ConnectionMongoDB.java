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
import org.bson.types.ObjectId;


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
                BasicDBObject query = new BasicDBObject("name", queryStr);
                BasicDBObject doc = (BasicDBObject) coll.findOne(query);

                //test end
                Artist tmpartist = new Artist(doc.get("_id").toString(), doc.get("name").toString(), doc.get("nationality").toString(),doc.get("albums").toString()); //testAlbums för att se om kolumnen fungerar i GUI.
                System.out.println(tmpartist.getAlbumList());
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

        DBCursor cursor = coll.find();
        try {
           
            while(cursor.hasNext()) {              
               System.out.println(cursor.next());
               String id = cursor.curr().get("_id").toString();
               String name = cursor.curr().get("name").toString();
               String nationality = cursor.curr().get("nationality").toString();
               String albums = cursor.curr().get("albums").toString();
               ArtistList.add(new Artist(id, name, nationality, albums));              
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

            try{
                 Album tmpalbum = new Album(doc.get("_id").toString(),doc.get("title").toString(), doc.get("genre").toString(), Integer.parseInt(doc.get("date").toString()), Float.valueOf(doc.get("rate").toString()), doc.get("artist").toString()); //testAlbums för att se om kolumnen fungerar i GUI.
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
        
        DBCursor cursor = coll.find();
        try {
            while(cursor.hasNext()) {              
               System.out.println(cursor.next());
               String title = cursor.curr().get("title").toString();
               String genre = cursor.curr().get("genre").toString();
               int date = Integer.valueOf( cursor.curr().get("date").toString() );
               float rate = Float.valueOf(cursor.curr().get("rate").toString());
               String theartitst = cursor.curr().get("artist").toString();
               String id = cursor.curr().get("_id").toString() ;
               
               AlbumList.add(new Album(id,title, genre, date, rate, theartitst));              
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
        if(artistExists(name) ) {
            throw new ArtistAlreadyExists(name);
        }
        else {
            DBCollection coll = db.getCollection("artist");
            BasicDBObject artist = new BasicDBObject();
            artist.put("name", name);
            artist.put("nationality", nationality);
            List<String> list = new ArrayList<>();
            list.add("not yet set");
            artist.put("albums", list);
            coll.insert(artist);
        }

    }
    
    private boolean artistExists(String name) {
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
    public void addAlbum(String title, String genre, String artistName, int date) throws ArtistDoesNotExistException, ArtistAlreadyMadeAlbum {
        if(!artistExists(artistName) ) {
            throw new ArtistDoesNotExistException(artistName);
        }
        else {
            String artistId = getArtistId(artistName);
            ObjectId artistObjId = new ObjectId(artistId);
            
            
            if(albumExists(title, genre, date)) {
                //Add artist to existing album
                String albumId = getAlbumId(title, genre, date);
                ObjectId albumObjId = new ObjectId(albumId);
                
                if(artistAlreadyMadeAlbum(albumObjId, artistName)){
                    throw new ArtistAlreadyMadeAlbum(title, genre, date, artistName);
                }
                else {

                    DBCollection coll = db.getCollection("album"); //Tar in alla album                
                    DBObject findAlbum = new BasicDBObject("_id", albumObjId);//Letar ett album

                    BasicDBObject updateAlbum = new BasicDBObject("$addToSet", 
                            new BasicDBObject("artist", artistName));
                    
                    try {
                        coll.update(findAlbum, updateAlbum);
                    }catch(Exception e){System.out.println("Update album failed.");}
                    
                }
            }
            
            else {                
                //Create new album and add artist to it
                DBCollection coll = db.getCollection("album");
                BasicDBObject album = new BasicDBObject();
                album.put("title", title);
                album.put("genre", genre);
                album.put("date", date);
                album.put("rate", 0);
                List<String> list = new ArrayList<>();
                list.add(artistName);
                album.put("artist", list);
                coll.insert(album);
            }
            
            //Add album to Artist
            
            DBCollection coll = db.getCollection("artist");
            DBObject findAlbum = new BasicDBObject("_id", artistObjId);
            BasicDBObject updateAlbum;
            if(firstAlbum(artistObjId)) {
                
                    BasicDBObject removeAlbum = new BasicDBObject("albums", -1);
                    BasicDBObject popQuery = new BasicDBObject("$pop", removeAlbum);
                    try {
                        coll.update(findAlbum, popQuery);
                    }catch(Exception e){System.out.println("Remove first album failed.");}    
                    BasicDBObject setQuery = new BasicDBObject("albums", title); 
                    
                    updateAlbum = new BasicDBObject("$addToSet", setQuery);
                
            }
            else {
                
                updateAlbum = new BasicDBObject("$addToSet",
                        new BasicDBObject("albums", title));
                
            }
            try {
                coll.update(findAlbum, updateAlbum);
            }catch(Exception e){System.out.println("Update artist failed.");}
        }
    }
    private boolean albumExists(String title, String genre, int date) {
        boolean exists = false;
        DBCollection coll = db.getCollection("album");
        
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> lista = new ArrayList<>();
        
        BasicDBObject titleQuery = new BasicDBObject("title", title);
        BasicDBObject genreQuery = new BasicDBObject("genre", genre);
        BasicDBObject dateQuery = new BasicDBObject("date", date);
        
        lista.add(titleQuery);
        lista.add(genreQuery);
        lista.add(dateQuery);
        query.put("$and", lista);
        
        long count = coll.count(query);
        try {
            if(count > 0 ) {
                exists = true;
            }
        }catch(Exception e){}
        return exists;
    }
    
    private String getAlbumId(String title, String genre, int date){
        String albumId = null;
        DBCollection coll = db.getCollection("album");
        
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> lista = new ArrayList<>();
        
        BasicDBObject titleQuery = new BasicDBObject("title", title);
        BasicDBObject genreQuery = new BasicDBObject("genre", genre);
        BasicDBObject dateQuery = new BasicDBObject("date", date);
        
        lista.add(titleQuery);
        lista.add(genreQuery);
        lista.add(dateQuery);
        query.put("$and", lista);
        
        DBCursor cursor = coll.find(query);
        try {
            if(cursor.hasNext()) {
                BasicDBObject dbo = (BasicDBObject)cursor.next();
                ObjectId id = (ObjectId)dbo.get("_id");
                albumId = id.toString();
            }
        }catch(Exception e){}
        finally {
            cursor.close();
            
        }
        
        return albumId;
    }
    private String getArtistId(String artistName) {
        String artistId = null;
        DBCollection coll = db.getCollection("artist");
        BasicDBObject artistQuery = new BasicDBObject("name", artistName);
        DBCursor cursor = coll.find(artistQuery);
        try {
            if(cursor.hasNext()) {
                BasicDBObject dbo = (BasicDBObject)cursor.next();
                ObjectId id = (ObjectId)dbo.get("_id");
                artistId = id.toString();
                        
                                                
            }
        }catch(Exception e){System.out.println("Adding artist to existing album failed.");}
        finally {
            cursor.close();

        }
        return artistId;
    }
    private boolean artistAlreadyMadeAlbum(ObjectId albumId, String artistName) {
        boolean made = false;
        
        DBCollection coll = db.getCollection("album"); //Tar in alla album                
        BasicDBObject findAlbum = new BasicDBObject("_id", albumId);//Letar ett album
        BasicDBObject findAlbumArtist = new BasicDBObject("madeby", artistName);
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> lista = new ArrayList<>();
 
        lista.add(findAlbum);
        lista.add(findAlbumArtist);
        query.put("$and", lista);
        
        long count = coll.count(query);
        if(count > 0) {

            made = true;
        }
        return made;
    }
    private boolean firstAlbum(ObjectId artistObjId){
        boolean first = false;
        DBCollection coll = db.getCollection("artist");
        BasicDBObject findArtist = new BasicDBObject("_id", artistObjId);
        BasicDBObject firstAlbum = new BasicDBObject("albums", "not yet set");
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> lista = new ArrayList<>();
        lista.add(findArtist);
        lista.add(firstAlbum);
        query.put("$and", lista);
        long count = coll.count(query);
        System.out.println("Count: " +count);
        if(count > 0) {
            first = true;
        }
        
        return first;
    }

    @Override
    public void rateAlbum(Album album, String score) {
        
        ObjectId id = new ObjectId(album.getID());
        
        DBCollection coll = db.getCollection("album");
        
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("rate", score));
        BasicDBObject searchQuery = new BasicDBObject().append("_id", id);
        
        coll.update(searchQuery, newDocument);
    }
    
    public void closeMongoDB()
    {
        mongoClient.close();
    }
}