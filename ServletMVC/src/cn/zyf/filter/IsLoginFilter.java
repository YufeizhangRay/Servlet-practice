package cn.zyf.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsLoginFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String path = req.getServletPath().substring(1);
		String autho = getFilterConfig().getInitParameter("authority");
		String noautho = getFilterConfig().getInitParameter("noautho");
		String[] autoArr = autho.split(",");
		String[] noautoArr = noautho.split(",");
		for(String str:noautoArr) {
			if(path.equals(str)) {
				//无需权限则放行
				chain.doFilter(req, resp);
			}
		}
		HttpSession session = req.getSession();
		for(String str:autoArr) {
			if(str.equals(path)) {
				String username = (String) session.getAttribute("user");
				//有权限且登录，则放行
				if(username!=null) {
					chain.doFilter(req, resp);
				}else {
					//没登录跳转login
					resp.sendRedirect(req.getContextPath() + "/login.jsp");
				}
			}
		}
	}	
}
