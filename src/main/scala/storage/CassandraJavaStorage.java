package storage;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

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
        session.execute("INSERT INTO usersks.users (user_id, fname, lname) VALUES (c37d661d-7e61-49ea-96a5-68c34e83db3a, 'Mario', 'Caster')");
    }
}
