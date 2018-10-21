package cn.zyf.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.zyf.mvc.model.Online;
import cn.zyf.mvc.model.User;
import cn.zyf.mvc.service.FactoryService;
import cn.zyf.mvc.service.OnlineService;
import cn.zyf.mvc.service.UserService;
import cn.zyf.mvc.utils.CookieUtils;

@SuppressWarnings("unused")
@WebServlet(urlPatterns = { "*.udo" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService userService = FactoryService.getUserService();
	OnlineService onlineService = FactoryService.getOnlineService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String mn = req.getServletPath();
		mn = mn.substring(1);
		mn = mn.substring(0, mn.length() - 4);
		try {
			Method method = this.getClass().getDeclaredMethod(mn, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		user.setUsername(req.getParameter("username"));
		user.setPasword(req.getParameter("pasword"));
		user.setAddress(req.getParameter("address"));
		user.setPhoneNo(req.getParameter("phoneNo"));
		user.setRegDate(new Date());
		int rows = userService.save(user);
		if (rows > 0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}

	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String address = req.getParameter("address");
		String phoneNo = req.getParameter("phoneNo");
		List<User> list = userService.query(username, address, phoneNo);
		req.setAttribute("userList", list);
		req.getRequestDispatcher("/main.jsp").forward(req, resp);
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		int rows = userService.deleteUserById(id);
		if (rows > 0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userService.get(id);
		req.setAttribute("user", user);
		req.getRequestDispatcher("/update.jsp").forward(req, resp);
	}

	private void updatedo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));

		User user = userService.get(id);
		String yUsername = user.getUsername();
		String xUseranme = req.getParameter("username");
		long cout = userService.getCountByName(xUseranme);
		if (!xUseranme.equals(yUsername) && cout > 0) {
			req.setAttribute("note", xUseranme + "已被占用!");
			req.getRequestDispatcher("/update.udo?id=" + id).forward(req, resp);
			return;
		}
		user.setUsername(xUseranme);
		user.setPasword(req.getParameter("pasword"));
		user.setAddress(req.getParameter("address"));
		user.setPhoneNo(req.getParameter("phoneNo"));
		int rows = userService.updateUserById(user);
		if (rows > 0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String pasword = req.getParameter("pasword");
		String expiredays = req.getParameter("expiredays");
		Cookie[] cookies = req.getCookies();
		boolean login = false;
		String account = null;
		String ssid = null;

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userKey")) {
					account = cookie.getValue();
				}

				if (cookie.getName().equals("ssid")) {
					ssid = cookie.getValue();
				}
			}
		}
		if (account != null && ssid != null) {
			login = ssid.equals(CookieUtils.md5Encrypt(username));
		}
		// 若非登录状态
		if (!login) {// 用户第一次登录必进
			User user = userService.login(username, pasword);
			// 若信息与数据库匹配
			if (user != null) {
				expiredays = expiredays == null ? "" : expiredays;
				switch (expiredays) {
				// 创建对应cookie信息
				case "7":
					CookieUtils.createCookie(username, req, resp, 7 * 24 * 60 * 60);
					break;
				case "30":
					CookieUtils.createCookie(username, req, resp, 30 * 24 * 60 * 60);
					break;
				case "100":
					CookieUtils.createCookie(username, req, resp, Integer.MAX_VALUE);
					break;
				default:
					CookieUtils.createCookie(username, req, resp, -1);
					break;
				}

				HttpSession session = req.getSession();
				session.setAttribute("user", user.getUsername());
				Online ol = onlineService.getOnlineBySsid(session.getId());
				if (ol != null) {
					ol.setUsername(username);
					onlineService.updateOnline(ol);
				}
				resp.sendRedirect(req.getContextPath() + "/main.jsp");
			} else {
				// 若不匹配
				req.setAttribute("note", "用户名或密码有误!");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
			// 若为登录状态
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("user", username);
			Online ol = onlineService.getOnlineBySsid(session.getId());
			if (ol != null) {
				ol.setUsername(username);
				onlineService.updateOnline(ol);
			}
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		}

	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		if (cookies != null && cookies.length > 0) {
			// 删除cookie
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userKey")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				if (cookie.getName().equals("ssid")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
		HttpSession session = req.getSession();
		// 删除session
		if (session != null) {
			session.removeAttribute("user");
		}
		resp.sendRedirect(req.getContextPath() + "/login.jsp");
	}

	public void online(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Online> list = onlineService.getAllOnline();
		req.setAttribute("online", list);
		req.getRequestDispatcher("/WEB-INF/jsp/online.jsp").forward(req, resp);
	}

}
