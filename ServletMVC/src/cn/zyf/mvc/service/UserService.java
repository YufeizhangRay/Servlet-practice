package cn.zyf.mvc.service;

import java.util.List;

import cn.zyf.mvc.model.User;

public interface UserService {
	
	/**
	 * 实现插入一条新用户信息数据
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * 根据用户编号删除对应的用户数据
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * 根据用户id修改对应用户信息数据
	 * @param id
	 * @return
	 */
	public int updateUserById(User user);
	
	
	/**
	 * 根据用户编号获取一条用户数据,封装成类User的一个对象
	 * @param id
	 * @return
	 */
	public User get(int id);
	
	/**
	 * 支持事务的
	 * @param conn
	 * @param id
	 * @return
	 */
	public User getTransation(int id);
	
	/**
	 * 获取所有的用户数据
	 * @return
	 */
	public List<User> getListAll();
	
	/**
	 * 查询指定用户名的用户有多少条
	 * @param username
	 * @return
	 */
	public long getCountByName(String username);

	/**
	 * 这是用户模糊查询的方法
	 * @param username
	 * @param address
	 * @param phoneNo
	 * @return
	 */
	public List<User> query(String username, String address, String phoneNo);

	/**
	 * 这是判断用户登录名和密码对不对的方法
	 * @param username
	 * @param pasword
	 * @return
	 */
	public User login(String username, String pasword);
}
