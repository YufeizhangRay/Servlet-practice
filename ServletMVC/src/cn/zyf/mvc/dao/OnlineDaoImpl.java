package cn.zyf.mvc.dao;

import java.util.List;

import cn.zyf.mvc.model.Online;

public class OnlineDaoImpl extends BaseDao<Online> implements OnlineDao {

	@Override
	public List<Online> getAllOnline() {
		String sql = "SELECT `ssid`,`username`,`page`,`ip`,`time` FROM `online`";
		return super.getList(sql);
	}

	@Override
	public void insertOnline(Online online) {
		String sql = "INSERT `online` SET `ssid`=?,"
				+ "`username`=?,`page`=?,`ip`=?,`time`=?";
		super.update(sql, online.getSsid(), online.getUsername(), 
				online.getPage(), online.getIp(), online.getTime());
	}

	@Override
	public void updateOnline(Online online) {
		String sql = "UPDATE `online` SET `username`=?,`page`=?,`ip`=?,`time`=? WHERE ssid=?";
		super.update(sql, online.getUsername(), 
				online.getPage(), online.getIp(), online.getTime(),online.getSsid());
	}

	@Override
	public void deleteExpiresOnline(String ssid) {
		String sql = "DELETE FROM `online` WHERE `ssid`=?";
		super.update(sql, ssid);
	}

	@Override
	public Online getOnlineBySsid(String ssid) {
		String sql = "SELECT `ssid`,`username`,`page`,`ip`,`time` FROM `online` WHERE ssid=?";
		return super.get(sql, ssid);
	}

}
