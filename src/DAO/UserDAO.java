package DAO;

import models.User;

import java.sql.*;

public class UserDAO extends DAO{

	public static User findUserById(int id){
		User user = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users WHERE id=" + id);
			if(res.next()) {
				user = new User(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
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
				user = new User(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
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
			String surname = user.getSurname();
			String password = user.getPassword();
			String email = user.getEmail();
			String avatar = user.getImagePath();
			PreparedStatement statement = connect().prepareStatement("INSERT INTO users(NAME, SURNAME, PASSWORD, EMAIL, AVATAR) VALUES  (?, ?, ?, ?, ?)");
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, password);
			statement.setString(4, email);
			statement.setString(5, avatar);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static int getId(User user){
		int id = 0;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users WHERE email='" + user.getEmail() + "'");
			if(res.next()){
				id = res.getInt(1);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return id;
	}

	public static void editName(User user, String name){
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE users SET name=? WHERE email=?");
			statement.setString(1, name);
			statement.setString(2, user.getEmail());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		user.setName(name);
	}

	public static void editSurname(User user, String surname){
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE users SET surname=? WHERE email=?");
			statement.setString(1, surname);
			statement.setString(2, user.getEmail());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		user.setSurname(surname);
	}

	public static void editEmail(User user, String email){
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE users SET email=? WHERE email=?");
			statement.setString(1, email);
			statement.setString(2, user.getEmail());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		user.setEmail(email);
	}

	public static void editAvatar(User user, String imagePath){
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE users SET avatar=? WHERE email=?");
			statement.setString(1, imagePath);
			statement.setString(2, user.getEmail());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		user.setImagePath(imagePath);
	}

}
