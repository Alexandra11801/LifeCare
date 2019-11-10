package Servlets;

import DAO.ArticleDAO;
import DAO.RevisionDAO;
import DAO.UserDAO;
import models.Article;
import models.Revision;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AcceptServlet")
public class AcceptServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Revision revision = RevisionDAO.findRevisionById(Integer.parseInt(request.getParameter("revision")));
		Article article = ArticleDAO.findArticleById(revision.getArticleId());
		ArticleDAO.change(article, revision.getText());
		RevisionDAO.deleteRevision(revision);
	}
}
