import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(name = "UserPageServlet")
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
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				request.getSession().setAttribute("current_user", cookie.getValue());
			}
		}
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
		root.put("authorizated", current_user != null);
		root.put("current_user", current_user);
		Helpers.render(request, response, "userpage.ftl", root);
	}
}
