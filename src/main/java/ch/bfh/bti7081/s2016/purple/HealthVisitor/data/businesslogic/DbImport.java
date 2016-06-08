package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;

/**
 * Created by gimmie7 on 27/05/16.
 */
public abstract class DbImport {

	static final Logger logger = LogManager.getLogger(HealthVisitorUI.class);

	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	static final String DB_URL = "jdbc:sqlite:test.db";
	static final int SQLITE_MAX_COMPOUND_SELECT = 500;
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "admin";

	@SuppressWarnings("resource")
	public static void importFromFile(String fileName, String tableName, char delimiter, boolean deleteFirst) {
		String line;
		Connection conn = null;
		Statement statement = null;

		try {
			// register JDBC driver
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			StringBuilder sb = new StringBuilder();
			String firstLine = "";
			int lineCount = 0;
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			while ((line = reader.readLine()) != null) {
				if (line.isEmpty())
					continue;

				if (delimiter != ',') {
					line = line.replace(delimiter, ',');
				}

				if (lineCount == 0) {
					if (deleteFirst) {
						sb.append(String.format("DELETE FROM %1$s;", tableName));
						firstLine = String.format(" INSERT INTO %1$s (%2$s) ", tableName, line);
						logger.debug(firstLine);
						deleteFirst = false;
					}
				} else if (lineCount == 1) {
					sb.append(firstLine);
					sb.append(String.format(" SELECT %1$s ", line));
				} else {
					sb.append(String.format(" UNION ALL SELECT %1$s ", line));
				}

				if (lineCount >= SQLITE_MAX_COMPOUND_SELECT) {
					statement = conn.createStatement();
					statement.executeUpdate(sb.toString());

					sb.setLength(0);
					lineCount = 1;
				} else {
					lineCount++;
				}
			}

			if (sb.length() > 0) {
				statement = conn.createStatement();
				statement.executeUpdate(sb.toString());
			}

			reader.close();

			logger.debug(String.format("Successfully imported data from %1$s into table %2$s", fileName, tableName));
		} catch (SQLException se) {
			// Handle errors for JDBC
			logger.error(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			logger.error(e.getMessage());
		} finally {
			// close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				logger.error(se.getMessage());
			}
		}
	}
}
