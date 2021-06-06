package comm.DatabaseSim;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Repository("sqlConnectoin")
public class SqlConnection implements DBconnection{

	private String URL;
	private String username;
	private String password;
	private Statement statement;
	private Connection connection;

	public SqlConnection() {
		URL = "jdbc:mysql://localhost:3306/notify";
		username = "root";
		password = "root";
		statement = null;
		try {
			connection = DriverManager.getConnection(URL, username, password);
			statement = connection.createStatement();
		} catch (SQLException throwables) {
			System.out.println("SQLException: " + throwables.getMessage());
			System.out.println("SQLState: " + throwables.getSQLState());
			System.out.println("VendorError: " + throwables.getErrorCode());
			throwables.printStackTrace();
		}
	}

	@Override
	public Connection getConnectoin() {
		return connection;
	}

	@Override
	public void setConnectoin(Connection cont) {
		this.connection = cont;
		
	}

	@Override
	public Statement getStatement() {
		return this.statement;
		
	}

	@Override
	public void setStatement(Statement stmt) {
		this.statement = stmt;
		
	}
	
}
