package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.UserDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import models.User;

@WebServlet(name = "Servlets.RegistrationServlet")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	public boolean emailValid(String email){
		Pattern pattern = Pattern.compile("([0-9]|[a-z]|[A-Z]|_|-|\\.)+@[a-z]+\\.[a-z]+");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean passwordValid(String password){
		Pattern pattern = Pattern.compile("([0-9]|[a-z]|[A-Z]){8,}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!request.getParameter("password").equals(request.getParameter("repeatpassword"))){
			response.sendRedirect("http://localhost:8080/registration?correct_password=false");
		}
		else{
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			Part part = request.getPart("avatar");
			String dirPath = getServletContext().getRealPath("") + "uploads";
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdir();
			}
			String[] data = part.getSubmittedFileName().split("\\.");
			String extension = data[data.length - 1];
			String image_name = Math.random() * 100000000 + "." + extension;
			String fullpath = dirPath + File.separator + image_name;
			part.write(fullpath);
			String path = File.separator + "uploads" + File.separator + image_name;
			if(UserDAO.findUserByEmail(email) != null){
				response.sendRedirect("http://localhost:8080/registration?user_exists=true");
			}
			else if(!emailValid(email)){
				response.sendRedirect("http://localhost:8080/registration?correct_email=false");
			}
			else if(!passwordValid(password)){
				response.sendRedirect("http://localhost:8080/registration?password_valid=false");
			}
			else {
				User newUser = new User(name, surname, ((Integer)password.hashCode()).toString(), email, path);
				UserDAO.addUser(newUser);
				if(request.getParameter("rememberMe") != null){
					Cookie cookie = new Cookie("user", Integer.toString(UserDAO.getId(newUser)));
					response.addCookie(cookie);
				}
				session.setAttribute("current_user", newUser);
				response.sendRedirect("http://localhost:8080/home");
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		root.put("correct_password", request.getParameter("correct_password") == null ? true : Boolean.parseBoolean(request.getParameter("correct_password")));
		root.put("password_valid", request.getParameter("password_valid") == null ? true : Boolean.parseBoolean(request.getParameter("password_valid")));
		root.put("correct_email", request.getParameter("correct_email") == null ? true : Boolean.parseBoolean(request.getParameter("correct_email")));
		root.put("user_exists", request.getParameter("user_exists") == null ? false : Boolean.parseBoolean(request.getParameter("user_exists")));
		root.put("authorizated", false);
		helpers.Helpers.render(request, response, "registration.ftl", root);
	}
}
