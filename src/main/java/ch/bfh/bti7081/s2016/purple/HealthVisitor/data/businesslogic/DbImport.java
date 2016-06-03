package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.*;

/**
 * Created by gimmie7 on 27/05/16.
 */
public abstract class DbImport {    

	static final Logger logger = LogManager.getLogger(HealthVisitorUI.class);
	
    static final String JDBC_DRIVER = "org.sqlite.JDBC";  
    static final String DB_URL = "jdbc:sqlite:test.db";

    //  Database credentials
    static final String USER = "";
    static final String PASS = "";

    public static void importFromFile(String fileName, String tableName, char delimiter, boolean deleteFirst){
    	BufferedReader br;
    	String line;

		Connection conn = null;
    	Statement stmt = null;
    	
    	try {
    		//register JDBC driver
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
    		String query = "";
    		br = new BufferedReader(new FileReader(fileName));
    		int count = 0;
    		String line1 = "";
    		
    		while ((line = br.readLine()) != null) {
    			if (line.isEmpty())
    				continue;
    			
    			if (delimiter != ','){
    				line = line.replace(delimiter, ',');
    			}
    			
    			if (count == 0){
    				if (deleteFirst){
    					query += "DELETE FROM " + tableName + "; ";
    					deleteFirst = false;
    					line1 = "INSERT INTO " + tableName + " (" + line + ")";
    				}
    				
    				query += line1;
    				count++;
    			}
    			else if (count == 1) {
    				query += " SELECT " + line;
    				count++;
    			}
    			else {
    				query += " UNION ALL SELECT " + line;
    				count++;
    			}
    			
    			if (count >= 500){
    		      	stmt = conn.createStatement();
    		      	stmt.executeUpdate(query);
    		      	
    		      	query = "";
    		      	count = 0;
    			}
			}
    		
    		if (query.length() > 0){
    			stmt = conn.createStatement();
		      	stmt.executeUpdate(query);
    		}
    		
	      	logger.debug("import from " + fileName + " into " + tableName + " successful");
    	} catch(SQLException se){
    	      //Handle errors for JDBC
    	      logger.error(se.getMessage());
    	} catch(Exception e){
    	      //Handle errors for Class.forName
    		logger.error(e.getMessage());
    	} finally{
    		//close resources
    		try {
    			if(stmt != null)
    				stmt.close();
    		}
    		catch(SQLException se2){
    			se2.printStackTrace();
    		}
    		try{
    			if (conn != null)
    				conn.close();
	      } catch(SQLException se){
				logger.error(se.getMessage());
	      }
	   }
    }
}
