package eu.comparegroup.maven.plugin.translations;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;

public class TranslationsLoader {
	private static class EnricoLogger {
		public void log(String message) {
			System.out.println(message);
		}

		public void error(String message) {
			System.out.println(message);
		}
	}
	private static EnricoLogger log = new EnricoLogger();

	private static String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	private Map<String, String> translations = new HashMap<>();

	private String jdbcConnectionUrl;
	
	private String jdbcConnectionUsername;
	
	private String jdbcConnectionPassword;
	
	private String translationsQuery;
	
	public TranslationsLoader(String jdbcConnectionUrl, String jdbcConnectionUsername, String jdbcConnectionPassword, String translationsQuery){
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.jdbcConnectionUsername = jdbcConnectionUsername;
		this.jdbcConnectionPassword = jdbcConnectionPassword;
		this.translationsQuery = translationsQuery;
	}
	
	public void loadTranslations() {
		try {
			Class.forName(MYSQL_JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(jdbcConnectionUrl, jdbcConnectionUsername, jdbcConnectionPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(translationsQuery);

			while (resultSet.next()) {
				String keyname = resultSet.getString("keyname");
				String keyvalue = resultSet.getString("keyvalue");
				translations.put(keyname, keyvalue);
			}
		} catch (ClassNotFoundException e) {
			log.error("A class was not found " + e.getMessage());
		} catch (SQLException e) {
			log.error("Sql exception in executing the query " + e.getMessage());
		} finally {
			// close connection, statement and result set
			// http://stackoverflow.com/questions/5809239/query-a-mysql-db-using-java
		}
	}

	public Map<String, String> getTranslations() {
		return translations;
	}
}
