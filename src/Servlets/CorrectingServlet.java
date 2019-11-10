package Servlets;

import DAO.ArticleDAO;
import DAO.RevisionDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import helpers.Helpers;
import models.Article;
import models.Revision;
import models.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "CorrectingServlet")
public class CorrectingServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		Article article = ArticleDAO.findArticleById(articleId);
		String text = request.getParameter("text");
		String description = request.getParameter("description");
		RevisionDAO.addRevision(new Revision(articleId, description, text));
		response.sendRedirect("http://localhost:8080/article?article=" + article.getTitle());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		User current_user = (User)request.getSession().getAttribute("current_user");
		String title = request.getParameter("article");
		Article article = ArticleDAO.findArticleByTitle(title);
		int articleId = ArticleDAO.getId(article);
		root.put("authorizated", request.getAttribute("authorizated"));
		root.put("current_user", current_user);
		root.put("article", article);
		root.put("articleId", articleId);
		Helpers.render(request, response,"correcting.ftl", root);
	}
}
