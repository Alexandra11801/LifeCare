package Servlets;

import DAO.ArticleDAO;
import DAO.UserDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "EditingServlet")
@MultipartConfig
public class EditingServlet extends HttpServlet {

	public void init(){
		Configuration conf = new Configuration();
		conf.setServletContextForTemplateLoading(this.getServletContext(), "/WEB-INF/templates");
		conf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		getServletContext().setAttribute("conf", conf);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = (new BufferedReader(new InputStreamReader(request.getPart("name").getInputStream()))).readLine();
		String surname = (new BufferedReader(new InputStreamReader(request.getPart("surname").getInputStream()))).readLine();
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
		System.out.println(dirPath + image_name);
		String path = File.separator + "uploads" + File.separator + image_name;
		User user = (User)request.getSession().getAttribute("current_user");
		UserDAO.editName(user, name);
		UserDAO.editSurname(user, surname);
		UserDAO.editEmail(user, email);
		UserDAO.editAvatar(user, path);
		response.sendRedirect("http://localhost:8080/user");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> root = new TreeMap<>();
		response.setContentType("text/html");
		User user = (User)request.getSession().getAttribute("current_user");
		root.put("user", user);
		root.put("authorizated", true);
		root.put("current_user", user);
		helpers.Helpers.render(request, response, "edit.ftl", root);
	}
}
