/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Query {

    private String queryString = null;
    private Connection connection;
    private boolean closeConnectionWhenDone = false;

    public Query(String queryString) {
        this.queryString = queryString;
    }

    public abstract void prepareQuery(PreparedStatement query) throws SQLException;

    public abstract Object onQueryResult(ResultSet rs) throws SQLException;

    public Object executeQuery() throws SQLException {
        Object obj = null;
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            query = prepareQuery(queryString);
            //Pass it to the class specific method
            prepareQuery(query);
            rs = executeQuery(query);
            //Pass it to the class specific method
            obj = onQueryResult(rs);
        } catch (SQLException ex) {
            closeQuery(query, rs);
            throw ex;
        } finally {
            closeQuery(query, rs);
        }
        return obj;
    }

    protected Connection getConnection(){
        return connection;
    }
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    public boolean isClosingWhenDone(){
        return closeConnectionWhenDone;
    }
    public void setClosingWhenDone(boolean closeConnectionWhenDone){
        this.closeConnectionWhenDone = closeConnectionWhenDone;
    }

    //To avoid every class being able to access the Database, this is set to
    //private so only predefined methods inside the class can use it
    private PreparedStatement prepareQuery(String queryString) throws SQLException {
        Connection con;
        PreparedStatement query = null;
        try {
            con = getConnection();
            query = con.prepareStatement(queryString);
        } catch (SQLException ex) {
            //safety close in case it is forgotten
            closeQuery(query, null);
            throw ex;
        }
        //connection is not closed intentionally, as it will be closed when 
        //executeQuery(PreparedStatement query) is called
        return query;
    }

    //To avoid every class being able to access the Database, this is set to
    //private so only predefined methods inside the class can use it
    private ResultSet executeQuery(PreparedStatement query) throws SQLException {
        ResultSet rs = null;
        try {
            boolean gotResult = query.execute();
            if (gotResult) {
                rs = query.getResultSet();
            }
        } catch (SQLException ex) {
            closeQuery(query, rs);
            //safety close in case it is forgotten
            throw ex;
        } finally {
            //The query is closed with the 
            //closeQuery(PreparedStatement query, ResultSet rs) method
        }
        return rs;
    }
    //Due to the use of executeQuery or prepareQuery which is private, this method is also set
    //to private as it it only used in conjunction with executeQuery or prepareQuery

    private void closeQuery(PreparedStatement query, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (query != null) {
                if(closeConnectionWhenDone){
                    if (query.getConnection() != null) {
                        query.getConnection().close();
                    }
                }
                query.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
