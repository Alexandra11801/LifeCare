package Servlets;

import DAO.ArticleDAO;
import DAO.CommentDAO;
import DAO.UserDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import models.Article;
import models.Comment;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name = "ArticleServlet")
public class ArticleServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String comm = request.getParameter("comm-text");
		Article article = ArticleDAO.findArticleByTitle(request.getParameter("article"));
		int articleId = ArticleDAO.getId(article);
		int senderId = UserDAO.getId((User)request.getSession().getAttribute("current_user"));
		int receiverId;
		if(!request.getParameter("receiver").equals("")) {
			receiverId = Integer.parseInt(request.getParameter("receiver"));
		}
		else{
			receiverId = 0;
		}
		LocalDate date = LocalDate.now();
		CommentDAO.addComment(new Comment(articleId, senderId, receiverId, comm, date));
		response.sendRedirect("http://localhost:8080/article?article=" + article.getTitle());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		User current_user = (User)request.getSession().getAttribute("current_user");
		String title = request.getParameter("article");
		Article article = ArticleDAO.findArticleByTitle(title);
		ArrayList<Comment> comments = CommentDAO.allComments(article);
		comments.sort(new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		root.put("authorizated", request.getAttribute("authorizated"));
		root.put("current_user", current_user);
		root.put("article", article);
		root.put("articleId", ArticleDAO.getId(article));
		root.put("author", ArticleDAO.getAuthor(article));
		root.put("comments", comments);
		helpers.Helpers.render(request, response, "article.ftl", root);
	}

}
