package cn.zyf.listener;

import java.util.Date;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.zyf.mvc.model.Online;
import cn.zyf.mvc.service.FactoryService;
import cn.zyf.mvc.service.OnlineService;


/*
 * 基本思路:
 * 1.当请求进来的时候, 记录访问者的信息,判断访问者,若在数据库中存在这个用户,更新它访问时间,访问页面,没有的话插入到数据库
 * 2.记录在线用户的时候,记录访问过来的时间, 10分钟, 如果过用户10分钟没有操作, 用户离线, 数据表中在线记录,删除
 * 3.访问者登录成功后,将online数据表里的username,从游客改为真正用户名
 * ServletContextLintener,web应用启动的时候, 要做每5秒钟检查过期用户,并且执行删除
 */
@WebListener
public class OnlineRequestListener implements ServletRequestListener {
	
	//Service对象,请求操作数据库
	OnlineService onlineService = FactoryService.getOnlineService();

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		//1.通过请求拿到访问者的基本信息
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session = request.getSession();
		//获取sessionid
		String ssid = session.getId();
		//获取访问者ip地址
		String ip = request.getRemoteAddr();
		//获取访问者正在访问的页面
		String page = request.getRequestURI();
		System.out.println("请求初始化方法开始了----" + page);
		//获取用户名称
		String username = (String) session.getAttribute("user");
		username = username==null?"游客":username;
		
		//操作数据库之前,把这些信息封装到一个Online对象
		Online ol = new Online();
		ol.setSsid(ssid);
		ol.setPage(page);
		ol.setIp(ip);
		ol.setUsername(username);
		ol.setTime(new Date());
		
		//链接数据库,把信息放到数据库,  有:更新time, 没有:插入新记录
		//1.根据ssid,查询数据库,有没有记录
		Online online = onlineService.getOnlineBySsid(ssid);
		if(online!=null) {
			//2.更新这条记录里time
			online.setTime(new Date());
			online.setPage(page);
			onlineService.updateOnline(online);
		}else {
			onlineService.insertOnline(ol);
		}
	}

}