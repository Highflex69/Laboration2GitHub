/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import com.mongodb.BasicDBObject;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author Teddy
 */
public class ConnectionMongoDB {
    private DB db;
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
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

        db = mongoClient.getDB( "labb2" ); //labb2 = database
    }
    
    
}

