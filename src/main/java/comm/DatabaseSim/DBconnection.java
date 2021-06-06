package comm.DatabaseSim;

import java.sql.Connection;
import java.sql.Statement;

public interface DBconnection {
	Connection getConnectoin();
	void setConnectoin(Connection cont);
	Statement getStatement();
	void setStatement(Statement stmt);
}
