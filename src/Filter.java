import models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter")
public class Filter implements javax.servlet.Filter {

	private FilterConfig conf;

	public void destroy() {
		conf = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		User current_user = (User)((HttpServletRequest)req).getSession().getAttribute("current_user");
		Cookie[] cookies = ((HttpServletRequest)req).getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				((HttpServletRequest)req).getSession().setAttribute("current_user", cookie.getValue());
			}
		}
		String part = ((HttpServletRequest)req).getRequestURL().toString().split("/")[3];
		if(part != null) {
			if (part.equals("new_article") || part.equals("correct") || part.equals("edit") || part.equals("revisions") || part.equals("user") && ((HttpServletRequest) req).getParameter("id") == null) {
				if (current_user == null) {
					((HttpServletRequest) req).setAttribute("authorizated", false);
					((HttpServletResponse) resp).sendRedirect("http://localhost:8080/authorization");
				}
			} else {
				((HttpServletRequest) req).setAttribute("authorizated", current_user != null);
			}
		}
	}

	public void init(FilterConfig config) throws ServletException {
		this.conf = config;
	}

}
