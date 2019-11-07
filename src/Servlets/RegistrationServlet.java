package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!request.getParameter("password").equals(request.getParameter("repeatpassword"))){
			response.sendRedirect("http://localhost:8080/registration?correct_password=false");
		}
		else{
			String name = (new BufferedReader(new InputStreamReader(request.getPart("name").getInputStream()))).readLine();
			String surname = (new BufferedReader(new InputStreamReader(request.getPart("surname").getInputStream()))).readLine();
			String password = (new BufferedReader(new InputStreamReader(request.getPart("password").getInputStream()))).readLine();
			String email = (new BufferedReader(new InputStreamReader(request.getPart("email").getInputStream()))).readLine();
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
			else {
				User newUser = new User(name, surname, password, email, path);
				if(Boolean.parseBoolean(request.getParameter("rememberMe"))){
					Cookie cookie = new Cookie("user", Integer.toString(UserDAO.getId(newUser)));
					cookie.setMaxAge(Integer.MAX_VALUE);
					response.addCookie(cookie);
				}
				UserDAO.addUser(newUser);
				session.setAttribute("current_user", newUser);
				response.sendRedirect("http://localhost:8080/home");
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		root.put("correct_password", request.getParameter("correct_password") == null ? true : Boolean.parseBoolean(request.getParameter("correct_password")));
		root.put("user_exists", request.getParameter("user_exists") == null ? false : Boolean.parseBoolean(request.getParameter("user_exists")));
		root.put("authorizated", false);
		helpers.Helpers.render(request, response, "registration.ftl", root);
	}
}
