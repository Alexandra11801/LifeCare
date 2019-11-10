package DAO;

import models.Article;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArticleDAO extends DAO {

	public static Article findArticleByTitle(String title){
		Article article = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM articles WHERE title='" + title + "'");
			if(res.next()){
				article = new Article(res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getInt(7));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return article;
	}

	public static Article findArticleById(int id){
		Article article = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM articles WHERE id='" + id + "'");
			if(res.next()){
				article = new Article(res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getInt(7));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return article;
	}

	public static void addArticle(Article article){
		try{
			int user_id = article.getUser_id();
			String title = article.getTitle();
			String text = article.getText();
			String category = article.getCategory();
			PreparedStatement statement = connect().prepareStatement("INSERT INTO articles (AUTHOR, TITLE, TEXT, CATEGORY) VALUES(?, ?, ?, ?)");
			statement.setInt(1, user_id);
			statement.setString(2, title);
			statement.setString(3, text);
			statement.setString(4, category);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static ArrayList<Article> allArticles(User user){
		ArrayList<Article> articles = new ArrayList<>();
		try{
			Statement statement = connect().createStatement();
			int user_id = UserDAO.getId(user);
			ResultSet res = statement.executeQuery("SELECT * FROM articles WHERE author=" + user_id);
			while(res.next()){
				articles.add(new Article(res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getInt(7)));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return articles;
	}

	public static User getAuthor(Article article){
		return UserDAO.findUserById(article.getUser_id());
	}

	public static int getId(Article article){
		int id = 0;
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM articles WHERE title=?");
			statement.setString(1, article.getTitle());
			ResultSet res = statement.executeQuery();
			if(res.next()){
				id = res.getInt(1);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return id;
	}

	public static void like(Article article){
		article.setLikes(article.getLikes() + 1);
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE articles SET likes=? WHERE title=?");
			statement.setInt(1, article.getLikes());
			statement.setString(2, article.getTitle());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}

	}

	public static void dislike(Article article){
		article.setDislikes(article.getDislikes() + 1);
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE articles SET dislikes=? WHERE title=?");
			statement.setInt(1, article.getDislikes());
			statement.setString(2, article.getTitle());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static void change(Article article, String newText){
		article.setText(newText);
		try{
			PreparedStatement statement = connect().prepareStatement("UPDATE articles SET text=? WHERE title=?");
			statement.setString(1, article.getText());
			statement.setString(2, article.getTitle());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

}
