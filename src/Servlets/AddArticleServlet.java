package Servlets;

import DAO.ArticleDAO;
import DAO.CategoryDAO;
import DAO.UserDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import helpers.Helpers;
import models.Article;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "AddArticleServlet")
public class AddArticleServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User author = (User)session.getAttribute("current_user");
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String articleText = request.getParameter("text");
		Article article = new Article(UserDAO.getId(author), title, articleText, category, 0, 0);
		ArticleDAO.addArticle(article);
		response.sendRedirect("http://localhost:8080/article?article=" + article.getTitle());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		root.put("categories", CategoryDAO.allCategories());
		root.put("authorizated", true);
		root.put("current_user", request.getSession().getAttribute("current_user"));
		Helpers.render(request, response, "add_article.ftl", root);
	}
}
