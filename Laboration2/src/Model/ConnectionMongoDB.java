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
import com.mongodb.client.FindIterable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import static java.util.concurrent.TimeUnit.SECONDS;
import org.bson.Document;

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
    private void getArtist() {
        
        DBCollection coll = db.getCollection("artist");
        Scanner scan = new Scanner(System.in);
        System.out.println("Search for artist: ");
        String queryStr = scan.nextLine();
        BasicDBObject query = new BasicDBObject("name", queryStr);
        DBCursor cursor = coll.find(query);
        try {
            while(cursor.hasNext() ) {
                BasicDBObject obj = (BasicDBObject)cursor.next();
                System.out.println(obj.getString("nationality"));
            }
        } finally {
            cursor.close();
        }
        //Document doc = new Document( db.getCollection("artist".find()))
        mongoClient.close();
        
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

