package Servlets;

import DAO.ArticleDAO;
import DAO.UserDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "Servlets.UserPageServlet")
public class UserPageServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		User user;
		User current_user = (User)request.getSession().getAttribute("current_user");
		if(request.getParameter("id") != null){
			user = UserDAO.findUserById(Integer.parseInt(request.getParameter("id")));
		}
		else{
			user = current_user;
		}
		root.put("user", user);
		root.put("articles", ArticleDAO.allArticles(user));
		root.put("authorizated", request.getAttribute("authorizated"));
		root.put("current_user", current_user);
		helpers.Helpers.render(request, response, "userpage.ftl", root);
	}
}
