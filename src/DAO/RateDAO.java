package DAO;

import models.Article;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RateDAO extends DAO {

	public static boolean isRated(User user, Article article){
		int userId = UserDAO.getId(user);
		int articleId = ArticleDAO.getId(article);
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM rated WHERE user_id=? AND article_id=? ");
			statement.setInt(1, userId);
			statement.setInt(2, articleId);
			ResultSet res = statement.executeQuery();
			return res.next();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public static void rate(User user, Article article){
		int userId = UserDAO.getId(user);
		int articleId = ArticleDAO.getId(article);
		try{
			PreparedStatement statement = connect().prepareStatement("INSERT INTO rated (user_id, article_id) VALUES(?, ?)");
			statement.setInt(1, userId);
			statement.setInt(2, articleId);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

}
