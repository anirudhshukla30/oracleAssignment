package dao;

public class DBConnection {

    /**
     * This method in reality would be actual ORM based entity which would fetch
     * from Database and return object Data
     *
     * @return
     */
    public DBMockUp getData() {
        DBMockUp db = new DBMockUp();
        db.initDataMockUp();
        return db;
    }
}
