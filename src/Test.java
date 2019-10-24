import java.sql.*;

public class Test {

	public static void main(String[] args){
		String email = "alexaver11801@gmail.com";
		User u = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/LifeCareDataBase",
					"postgres",
					"postgres"
			);

			Statement stmnt = conn.createStatement();
			ResultSet res = stmnt.executeQuery("select * from users where email='" + email + "'");
			if(res.next()) {
				u = new User(res.getString(2), res.getString(3), res.getString(4), res.getString(5), null);
			}
			System.out.println(u == null);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
