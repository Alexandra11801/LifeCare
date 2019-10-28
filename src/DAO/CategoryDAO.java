package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDAO extends DAO{

	public static ArrayList<String> allCategories(){
		ArrayList<String> categories = new ArrayList<>();
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT category FROM categories");
			while (res.next()){
				categories.add(res.getString(1));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return categories;
	}

}
