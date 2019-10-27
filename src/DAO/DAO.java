package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

	public static Connection connect(){
		Connection con = null;
		try{
			Class.forName("org.postgresql.Driver");
			try {
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LifeCareDataBase", "postgres", "post");
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return con;
	}

}
