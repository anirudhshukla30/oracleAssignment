package dao;

import java.util.*;

/**
 * Defining a Basic Object pool pattern of DBConnections with a singleton DBPool
 * instance
 */
public class DBPool {
    // Singleton object
    private static DBPool singletonPool;
    // Map to hold the connection parameters, in a more robust scenario this Integer
    // can hold the Client IDs requesting connections
    private Map<Integer, DBConnection> dbConnections = new HashMap<Integer, DBConnection>();

    private DBPool() {
        setup();
    }

    /**
     * To fetch the singleton instance
     *
     * @return
     */
    public static DBPool getInstance() {
        if (singletonPool == null) {
            synchronized (DBPool.class) {
                if (singletonPool == null) {
                    singletonPool = new DBPool();
                }
            }
        }
        return singletonPool;
    }

    /**
     * Setting up the Data Pool
     */
    private void setup() {
        for (int i = 1; i <= 5; i++) {
            dbConnections.put(i, new DBConnection());
        }
    }

    /**
     * Getting a new connection, as of now no checks on max connections, release
     * connection are provided as that is not in scope of problem statement
     *
     * @param clientId
     * @return
     */
    public DBConnection acquireConnection(int clientId) {
        return this.dbConnections.get(clientId % 5);
    }
}
