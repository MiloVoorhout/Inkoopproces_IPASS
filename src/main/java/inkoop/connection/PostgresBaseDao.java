 package inkoop.connection;


import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PostgresBaseDao {
    protected static Connection result;

    protected final Connection getConnection() {
    	if(result != null) {
    		return result;
    	}
    	        
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
            result = ds.getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }
}
