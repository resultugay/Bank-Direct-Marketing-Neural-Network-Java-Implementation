package bank_marketing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class bank_dao {
	static	Connection  conn;

		public static Connection connect(String host,String databaseName,String user,String pass){
			String url = "jdbc:mysql://"+host+"/"+ databaseName;
			try {
				Class.forName ("com.mysql.jdbc.Driver").newInstance ();
				conn = DriverManager.getConnection (url, user, pass);
				
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Access denied for user " + user +"\nPlease check your password or username" );
				e.printStackTrace();
			}
			return conn;
		}
		public void closeConnection(Connection conn){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
