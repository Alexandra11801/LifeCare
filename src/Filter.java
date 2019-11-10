import DAO.UserDAO;
import models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebFilter(filterName = "Filter")
public class Filter implements javax.servlet.Filter {

	private FilterConfig conf;

	public void destroy() {
		conf = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		User current_user = (User)((HttpServletRequest)req).getSession().getAttribute("current_user");
		if(current_user == null) {
			Cookie[] cookies = ((HttpServletRequest) req).getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					current_user = UserDAO.findUserById(Integer.parseInt(cookie.getValue()));
					((HttpServletRequest) req).getSession().setAttribute("current_user", current_user);
				}
			}
		}
		if(current_user == null){
			current_user = new User("Anonymous", "", "", "", "uploads" + File.separator + "avatar.png");
			((HttpServletRequest) req).getSession().setAttribute("current_user", current_user);
		}
		String part = ((HttpServletRequest)req).getRequestURL().toString().split("/")[3];
		if(part != null) {
			if (part.equals("new_article") || part.equals("correct") || part.equals("edit") || part.equals("revisions") || part.equals("user") && ((HttpServletRequest) req).getParameter("id") == null) {
				if (current_user .getName().equals("Anonymous")) {
					((HttpServletResponse) resp).sendRedirect("http://localhost:8080/authorization");
				}
			}
			((HttpServletRequest) req).setAttribute("authorizated", !current_user.getEmail().equals(""));
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
		this.conf = config;
	}

}
