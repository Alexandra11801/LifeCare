import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import sun.security.util.Password;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "RegistrationServlet")
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
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			System.out.println(name);
			Part filePart = request.getPart("avatar");
			String imageName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream avatar = filePart.getInputStream();
			if(UserDAO.findUserByEmail(email) != null){
				response.sendRedirect("http://localhost:8080/registration?user_exists=true");
			}
			else {
				User newUser = new User(name, surname, password, email, avatar);
				newUser.setImageName(imageName);
				UserDAO.addUser(newUser);
				session.setAttribute("current_user", newUser);
				response.sendRedirect("http://localhost:8080/");
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		root.put("correct_password", request.getParameter("correct_password") == null ? true : Boolean.parseBoolean(request.getParameter("correct_password")));
		root.put("user_exists", request.getParameter("user_exists") == null ? false : Boolean.parseBoolean(request.getParameter("user_exists")));
		root.put("authorizated", false);
		Helpers.render(request, response, "registration.ftl", root);
	}
}
