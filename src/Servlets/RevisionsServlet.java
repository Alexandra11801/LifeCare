package Servlets;

import DAO.RevisionDAO;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import helpers.Helpers;
import models.Revision;
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

@WebServlet(name = "RevisionsServlet")
public class RevisionsServlet extends HttpServlet {

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
		response.setContentType("text/html; charset=UTF-8");
		User current_user = (User)request.getSession().getAttribute("current_user");
		ArrayList<Revision> revisions = RevisionDAO.allRevisions(current_user);
		root.put("authorizated", request.getAttribute("authorizated"));
		root.put("current_user", current_user);
		root.put("revisions", revisions);
		Helpers.render(request, response, "revisions.ftl", root);
	}
}
