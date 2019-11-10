package models;

import DAO.CommentDAO;

import java.time.LocalDate;
import java.util.Date;

public class Comment implements Comparable{

	private  int articleId;
	private  int senderId;
	private int receiverId;
	private String text;
	private LocalDate date;
	private User sender;
	private Comment receiver;
	private String senderAvatar;
	private String senderName;
	private int id;


	public Comment(int articleId, int senderId, int receiverId, String text, LocalDate date) {
		this.articleId = articleId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.text = text;
		this.date = date;
		sender = CommentDAO.getSender(this);
		receiver = CommentDAO.getReceiver(this);
		senderAvatar = sender.getImagePath();
		senderName = sender.getName() + sender.getSurname();
		id = CommentDAO.getId(this);
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Comment){
			return this.date.compareTo(((Comment) o).date);
		}
		return 0;
	}


	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Comment getReceiver() {
		return receiver;
	}

	public void setReceiver(Comment receiver) {
		this.receiver = receiver;
	}

	public String getSenderAvatar() {
		return senderAvatar;
	}

	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
