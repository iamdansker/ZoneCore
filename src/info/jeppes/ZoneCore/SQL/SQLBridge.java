/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.SQL;

import info.jeppes.ZoneCore.ZoneCore;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeppe
 */
public class SQLBridge {
        
    private final String url;
    private final int port;
    private final String RDBMS;
    private final String database;
    private final String username;
    private final String password;
    
    private Connection connection = null;
    
    
	public SQLBridge() {
        this.url = ZoneCore.getCorePlugin().getConfig().getString("SQL.url");
        this.port = ZoneCore.getCorePlugin().getConfig().getInt("SQL.port");
        this.RDBMS = ZoneCore.getCorePlugin().getConfig().getString("SQL.RDBMS");
        this.database = ZoneCore.getCorePlugin().getConfig().getString("SQL.database");
        this.username = ZoneCore.getCorePlugin().getConfig().getString("SQL.username");
        this.password = ZoneCore.getCorePlugin().getConfig().getString("SQL.password");
	}
	/**
     *
     * @param url URL to the SQL database
     * @param RDBMS Relational Database Management System (RDBMS) name
     * @param database Name of the database
     * @param username Username for the database
     * @param password Password for the database
     */
    public SQLBridge(String url, int port, String RDBMS, String database, String username, String password) {
        this.url = url;
        this.port = port;
        this.RDBMS = RDBMS;
        this.database = database;
        this.username = username;
        this.password = password;
	}

	protected Connection getNewConnection() throws SQLException {
        String databaseURL = "jdbc:"+RDBMS+"://"+url+":"+port+"/"+database;
		return DriverManager.getConnection(databaseURL, username, password);
	}
	protected Connection getConnection() throws SQLException {
        if(connection != null){
            if(!connection.isClosed()){
                return connection;
            }
        }
        connection = getNewConnection();
        return connection;
	}
    
    public Object executeQuery(Query query){
		try {
            query.setConnection(getConnection());
            query.setClosingWhenDone(false);
			return query.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(SQLBridge.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
    }

	public static String md5(String input) throws NoSuchAlgorithmException {
            String result = input;
            if (input != null) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) {
                        result = "0" + result;
                }
            }
            return result;
	}

}
