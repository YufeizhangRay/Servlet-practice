package cn.zyf.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.zyf.mvc.utils.CookieUtils;

public class AutoLoginFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		Cookie[] cookies = req.getCookies();
		//有cookies
		if(cookies != null && cookies.length > 0) {
			String username = null;
			String ssid = null;
			for(Cookie c:cookies) {
				if(c.getName().equals("userKey")) {
					username = c.getValue();
				}
				if(c.getName().equals("ssid")) {
					ssid = c.getValue();
				}
			}
			
			if(username!=null && ssid!=null && ssid.equals(CookieUtils.md5Encrypt(username))) { 
				HttpSession session = req.getSession();
				session.setAttribute("user", username);
				resp.sendRedirect(req.getContextPath() + "/main.jsp");  //自动登录
			}else {
				chain.doFilter(req, resp);//拦截放行，显示login页面
			}
			
		}else {
			//无cookie
			chain.doFilter(req, resp); //拦截放行，显示login页面
		}
	}

}