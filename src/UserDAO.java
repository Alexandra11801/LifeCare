import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class UserDAO {

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

	public static User findUserById(int id){
		User user = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users WHERE id=" + id);
			if(res.next()) {
				user = new User(res.getString(2), res.getString(3), res.getString(4), res.getString(5), null);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return user;
	}

	public static User findUserByEmail(String email){
		User user = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users WHERE email='" + email + "'");
			if(res.next()) {
				user = new User(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getBinaryStream(6));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return user;
	}

	public static void addUser(User user){
		try{
			String name = user.getName();
			String surname = user.getSurmname();
			String password = user.getPassword();
			String email = user.getEmail();
			InputStream avatar = user.getAvatar();
			PreparedStatement statement = connect().prepareStatement("INSERT INTO users(NAME, SURNAME, PASSWORD, EMAIL, AVATAR) VALUES  (?, ?, ?, ?, ?)");
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, password);
			statement.setString(4, email);
			try {
				statement.setBinaryStream(5, avatar, avatar.available());
			}
			catch (IOException e){
				e.printStackTrace();
			}
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

}
