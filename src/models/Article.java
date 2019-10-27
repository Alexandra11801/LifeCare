package models;

public class Article {

	private int user_id;
	private String title;
	private String text;
	private String category;

	public Article(int user_id, String title, String text, String category) {
		this.user_id = user_id;
		this.title = title;
		this.text = text;
		this.category = category;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
