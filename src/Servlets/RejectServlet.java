package Servlets;

import DAO.ArticleDAO;
import DAO.RevisionDAO;
import models.Article;
import models.Revision;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RejectServlet")
public class RejectServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Revision revision = RevisionDAO.findRevisionById(Integer.parseInt(request.getParameter("revision")));
		RevisionDAO.deleteRevision(revision);
	}
}
