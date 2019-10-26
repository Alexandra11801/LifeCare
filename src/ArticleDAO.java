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
			ResultSet res = statement.executeQuery("SELECT * FROM ARTICLE WHERE title='" + title + "'");
			if(res.next()){
				article = new Article(res.getInt(2), res.getString(3), res.getString(4), res.getString(5));
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
				articles.add(new Article(res.getInt(2), res.getString(3), res.getString(4), res.getString(5)));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return articles;
	}

}
