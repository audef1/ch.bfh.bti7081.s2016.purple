package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static void importFromFile(String fileName, String tableName, boolean deleteFirst){
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try {
    		String query = "";
    		br = new BufferedReader(new FileReader(fileName));
    		int count = 0;
    		
    		while ((line = br.readLine()) != null) {
    			if (count == 0){
    				if (deleteFirst){
    					query += "DELETE FROM " + tableName + "; ";
    				}
    				query += "INSERT INTO " + tableName + " (" + line + ")";
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
			}
    		
			//register JDBC driver
			Class.forName(JDBC_DRIVER);

	      	conn = DriverManager.getConnection(DB_URL, USER, PASS);

	      	stmt = conn.createStatement();
	      
	      	stmt.executeUpdate(query);
	      	logger.debug("import from " + fileName + " into " + tableName + " successful");
    	} catch(SQLException se){
    	      //Handle errors for JDBC
    	      se.printStackTrace();
    	} catch(Exception e){
    	      //Handle errors for Class.forName
    		e.printStackTrace();
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
	    	  se.printStackTrace();
	      }
	   }
    }
}
