package models;

import DAO.ArticleDAO;
import DAO.RevisionDAO;

public class Revision {

	private int articleId;
	private String description;
	private String text;
	private String articleTitle;
	private int id;

	public Revision(int articleId, String description, String text) {
		this.articleId = articleId;
		this.text = text;
		this.description = description;
		articleTitle = ArticleDAO.findArticleById(articleId).getTitle();
		id = RevisionDAO.getId(this);
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
