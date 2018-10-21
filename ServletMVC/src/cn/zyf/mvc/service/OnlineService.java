package cn.zyf.mvc.service;

import java.util.List;

import cn.zyf.mvc.model.Online;

public interface OnlineService {
	/**
	 * 取出所有在线访问者信息
	 * 
	 * @return
	 */
	public List<Online> getAllOnline();

	/**
	 * 插入一条Online信息
	 * 
	 * @param online
	 */
	public void insertOnline(Online online);

	/**
	 * 更新保存的online信息
	 * 
	 * @param online
	 */
	public void updateOnline(Online online);

	/**
	 * 根据ssid删除下限用户的online信息
	 * 
	 * @param ssid
	 */
	public void deleteExpiresOnline(List<Online> list);

	/**
	 * 根据ssid获取一条online信息
	 * 
	 * @param ssid
	 * @return
	 */
	public Online getOnlineBySsid(String ssid);
}
