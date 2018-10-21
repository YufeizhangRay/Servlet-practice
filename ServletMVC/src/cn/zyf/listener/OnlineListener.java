package cn.zyf.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListener implements HttpSessionListener,HttpSessionAttributeListener {

	@SuppressWarnings("unchecked")
	@Override
	//登录的时候触发
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map<String, String> online = (Map<String, String>) application.getAttribute("online");
		if(online==null) {
			online = new HashMap<>();
		}
		String username = session.getAttribute("user").toString();
		online.put(session.getId(), username);
		application.setAttribute("online", online);
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	//访问login页面时候触发 无需登录
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map<String, String> online = (Map<String, String>) application.getAttribute("online");
		if(online==null) {
			online = new HashMap<>();
		}
		String username = session.getAttribute("user").toString();
		username = username==null?"游客":username;
		online.put(session.getId(), username);
		application.setAttribute("online", online);
	}

	@SuppressWarnings("unchecked")
	@Override
	//注销时触发
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		Map<String, String> online = (Map<String, String>) application.getAttribute("online");
		if(online!=null) {
			online.remove(session.getId());
		}
		application.setAttribute("online", online);
	}
	
}