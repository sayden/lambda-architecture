package storage;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * Created by mariocaster on 10/17/15.
 */
public class CassandraJavaStorage {
    private Cluster cluster;
    private Session session;

    public void connect(String node){
        cluster = Cluster.builder().addContactPoint(node).build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Cluster: %s\n", metadata.getClusterName());
        for ( Host host : metadata.getAllHosts() ) {
            System.out.printf("Host: %s \n",host.getAddress());
        }
        session = cluster.connect();
        session.execute("USE mykeyspace");
    }

    public void close(){
        cluster.close();
    }

    public void loadData(){
        session.execute("INSERT INTO users (user_id, fname, lname) VALUES (1233, 'Mario', 'Caster')");
    }
}
