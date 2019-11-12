package Servlets;

import DAO.UserDAO;
import com.sun.corba.se.impl.corba.CORBAObjectImpl;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "Servlets.AuthorizationServlet")
@MultipartConfig
public class AuthorizationServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = UserDAO.findUserByEmail(email);
		HttpSession session = request.getSession();
		if(user == null){
			response.sendRedirect("http://localhost:8080/authorization?user_exists=false");
		}
		else if(!((Integer)password.hashCode()).toString().equals(user.getPassword())){
			response.sendRedirect("http://localhost:8080/authorization?correct_password=false");
		}
		else{
			if(request.getParameter("rememberMe") != null){
				Cookie cookie = new Cookie("user", Integer.toString(UserDAO.getId(user)));
				cookie.setMaxAge(1000000000);
				response.addCookie(cookie);
			}
			session.setAttribute("current_user", user);
			response.sendRedirect("http://localhost:8080/home");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");

		root.put("correct_password", request.getParameter("correct_password") == null ? true : Boolean.parseBoolean(request.getParameter("correct_password")));
		root.put("user_exists", request.getParameter("user_exists") == null ? true : Boolean.parseBoolean(request.getParameter("user_exists")));
		root.put("authorizated", false);
		helpers.Helpers.render(request, response, "authorization.ftl", root);
	}
}
