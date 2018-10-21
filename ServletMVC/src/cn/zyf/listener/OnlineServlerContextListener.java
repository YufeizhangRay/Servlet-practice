package cn.zyf.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.swing.Timer;

import cn.zyf.mvc.model.Online;
import cn.zyf.mvc.service.FactoryService;
import cn.zyf.mvc.service.OnlineService;

@WebListener
public class OnlineServlerContextListener implements ServletContextListener {
	/**
	 * 10分钟, 访问者超过设置的时间毫秒,没有操作,离线
	 */
	public final int MAX_MILLIS = 10 * 60 * 1000;

	OnlineService onlineService = FactoryService.getOnlineService();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 存放过时的访问者信息
		List<Online> expiresUserList = new ArrayList<>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 服务器启动的时候就被执行
		// 定时器
		new Timer(5 * 1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//得到数据库所有记录
				List<Online> list = onlineService.getAllOnline();
				if (list != null && list.size() > 0) {
					Date exDate = null;
					for (Online ol : list) {
						//获得所有初始时间
						exDate = ol.getTime();
						simpleDateFormat.format(exDate);
						Long exMilles;
						try {
							exMilles = simpleDateFormat.parse(exDate.toString()).getTime();
							if ((System.currentTimeMillis() - exMilles) > MAX_MILLIS) {
								//若过期加入过期列表
								expiresUserList.add(ol);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				// 从数据库中删除掉过时的访问者信息
				onlineService.deleteExpiresOnline(expiresUserList);
				//数据库中删除过时的访问者信息
				expiresUserList.clear();
			}
		}).start();

	}

}