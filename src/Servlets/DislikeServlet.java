package Servlets;

import DAO.ArticleDAO;
import DAO.RateDAO;
import DAO.UserDAO;
import models.Article;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DislikeServlet")
public class DislikeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Article article = ArticleDAO.findArticleByTitle(request.getParameter("article"));
		JSONObject obj = new JSONObject();
		User user = UserDAO.findUserByEmail(request.getParameter("user"));
		if(!RateDAO.isRated(user, article)) {
			ArticleDAO.dislike(article);
			RateDAO.rate(user, article);
		}
		try {
			obj.put("dislike", article.getDislikes());
			response.setContentType("text/json");
			response.getWriter().write(obj.toString());
		}
		catch (JSONException e){
			e.printStackTrace();
		}
	}
}
