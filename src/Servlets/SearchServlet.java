package Servlets;

import DAO.ArticleDAO;
import DAO.CategoryDAO;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "SearchServlet")
public class SearchServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pattern = request.getParameter("pattern");
		String category = request.getParameter("category");
		String catParameter = category.equals("All categories") ? "" : "&cat=" + category;
		response.sendRedirect("http://localhost:8080/search?search=" + pattern + catParameter);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		User current_user = (User)request.getSession().getAttribute("current_user");
		String pattern = request.getParameter("search");
		String category = request.getParameter("cat");
		ArrayList<Article> articles;
		if(pattern == null){
			articles = new ArrayList<>();
		}
		else if(category == null){
			articles = ArticleDAO.getByPattern(pattern);
		}
		else{
			articles = ArticleDAO.getByPatternWithCategory(pattern, category);
		}
		root.put("authorizated", request.getAttribute("authorizated"));
		root.put("current_user", current_user);
		root.put("articles", articles);
		root.put("categories", CategoryDAO.allCategories());
		Helpers.render(request, response,"search.ftl", root);
	}
}
