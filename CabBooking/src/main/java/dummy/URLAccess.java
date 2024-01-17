package dummy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLAccess implements Filter
{
	Logger logger=Logger.getLogger("URLAccessFilter.class");
	@Override public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	@Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest req=(HttpServletRequest) servletRequest;
		HttpServletResponse res=(HttpServletResponse) servletResponse;
		String requestURI = req.getRequestURI();
		logger.log(Level.INFO,"Uri: "+requestURI);
		Cookie[] cookies=req.getCookies();
		if (cookies == null)
		{
			//res.sendRedirect("");
			filterChain.doFilter(req, res);
			return;
		}
		String role=null;
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals("role"))
			role=cookie.getValue();
		}
		logger.log(Level.INFO,"cook"+cookies.toString());
		if(requestURI.matches("/index.html") || requestURI.matches("/") || requestURI.matches("/unauthorized.html") )
		{
			if(role!=null && role.equals("customer")){
				res.sendRedirect("/booking.html");
			}
			else if(role!=null && role.equals("admin")){
				res.sendRedirect("/dashboard.html");
			}
			filterChain.doFilter(req, res);
			return;
		}
		else if(role!=null && role.equals("customer") && requestURI.matches("/booking.html"))
		{
			filterChain.doFilter(req, res);
			return;
		}
		else if(role!=null && role.equals("admin")){
			filterChain.doFilter(req, res);
			return;
		}
		else {
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			req.getRequestDispatcher("unauthorized.html").forward(req, res);
		}

	}

	@Override public void destroy()
	{

	}
}
