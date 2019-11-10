package DAO;

import models.Article;
import models.Revision;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RevisionDAO extends DAO {

	public static void addRevision(Revision revision){
		try{
			PreparedStatement statement = connect().prepareStatement("INSERT INTO corrections(article, text, description) VALUES (?, ?, ?)");
			statement.setInt(1, revision.getArticleId());
			statement.setString(2, revision.getText());
			statement.setString(3, revision.getDescription());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static ArrayList<Revision> revisionsForArticle(Article article){
		ArrayList<Revision> revisions = new ArrayList<>();
		int articleId = ArticleDAO.getId(article);
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM corrections WHERE article=?");
			statement.setInt(1, articleId);
			ResultSet res = statement.executeQuery();
			while (res.next()){
				revisions.add(new Revision(res.getInt(2), res.getString(3), res.getString(4)));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return revisions;
	}

	public static ArrayList<Revision> allRevisions(User user){
		ArrayList<Article> allArticles = ArticleDAO.allArticles(user);
		ArrayList<Revision> allRevisions = new ArrayList<>();
		for(Article article : allArticles){
			allRevisions.addAll(revisionsForArticle(article));
		}
		return allRevisions;
	}

	public static Revision findRevision(int articleId, String text){
		Revision revision = null;
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM corrections WHERE article=? AND text=?");
			statement.setInt(1, articleId);
			statement.setString(2, text);
			ResultSet res = statement.executeQuery();
			if(res.next()){
				revision = new Revision(res.getInt(2), res.getString(3), res.getString(4));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return revision;
	}

	public static void deleteRevision(Revision revision){
		try{
			PreparedStatement statement = connect().prepareStatement("DELETE FROM corrections WHERE article=? AND text=?");
			statement.setInt(1, revision.getArticleId());
			statement.setString(2, revision.getText());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static int getId(Revision revision){
		int id = 0;
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM corrections WHERE article=? AND text=?");
			statement.setInt(1, revision.getArticleId());
			statement.setString(2, revision.getText());
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

	public static Revision findRevisionById(int id){
		Revision revision = null;
		try{
			PreparedStatement statement = connect().prepareStatement("SELECT * FROM corrections WHERE id=?");
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			if(res.next()){
				revision = new Revision(res.getInt(2), res.getString(3), res.getString(4));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return revision;
	}

}
