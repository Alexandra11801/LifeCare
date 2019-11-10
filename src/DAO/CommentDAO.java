package DAO;

import models.Article;
import models.Comment;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class CommentDAO extends DAO {

	public static User getSender(Comment comment){
		return UserDAO.findUserById(comment.getSenderId());
	}

	public static Comment getReceiver(Comment comment){
		return findCommentById(comment.getReceiverId());
	}

	public static Comment findCommentById(int id){
		Comment comment = null;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM commentaries WHERE id=" + id);
			if(res.next()) {
				comment = new Comment(res.getInt(2), res.getInt(3), res.getInt(4), res.getString(5), res.getDate(6).toLocalDate());
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return comment;
	}

	public static void addComment(Comment comment){
		try{
			PreparedStatement statement = connect().prepareStatement("INSERT INTO commentaries(article, sender, receiver, text, date) VALUES (?, ?, ?, ?, ?)");
			statement.setInt(1, comment.getArticleId());
			statement.setInt(2, comment.getSenderId());
			statement.setInt(3, comment.getReceiverId());
			statement.setString(4, comment.getText());
			statement.setDate(5, java.sql.Date.valueOf(comment.getDate()));
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static ArrayList<Comment> allComments(Article article){
		ArrayList<Comment> comments = new ArrayList<>();
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM commentaries WHERE article=" + ArticleDAO.getId(article));
			while(res.next()){
				comments.add(new Comment(res.getInt(2), res.getInt(3), res.getInt(4), res.getString(5), res.getDate(6).toLocalDate()));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return comments;
	}

	public static Comment firstReceiver(Comment comment){
		Comment comm = findCommentById(comment.getReceiverId());
		while(comm != null && comm.getReceiverId() != 0){
			comm = findCommentById(comm.getReceiverId());
		}
		return comm;
	}


	public static TreeMap<Comment, ArrayList<Comment>> commentsMap(Article article){
		ArrayList<Comment> comments = allComments(article);
		comments.sort(new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		TreeMap<Comment, ArrayList<Comment>> map = new TreeMap<>();
		for(Comment comment : comments){
			Comment rec = firstReceiver(comment);
			if(rec == null){
				map.put(comment, new ArrayList<>());
			}
			else{
				map.get(rec).add(comment);
			}
		}
		return map;
	}

	public static int getId(Comment comment){
		int id = 0;
		try{
			Statement statement = connect().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM commentaries WHERE sender='" + comment.getSenderId() + "' AND date='" + comment.getDate() + "'");
			if(res.next()){
				id = res.getInt(1);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return id;
	}

}
