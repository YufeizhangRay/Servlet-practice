package cn.zyf.mvc.dao;

import java.sql.Connection;
import java.util.List;

import cn.zyf.mvc.model.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public int save(User user) {
		String sql = "INSERT INTO `users`(`username`,`pasword`,`phone_no`,`address`,`reg_date`)VALUES(?,?,?,?,?);";
		return super.update(sql, user.getUsername(), user.getPasword(), user.getPhoneNo(), user.getAddress(),
				user.getRegDate());
	}

	@Override
	public int deleteUserById(int id) {
		String sql = "DELETE FROM `users` WHERE `id`=?;";
		return super.update(sql, id);
	}

	@Override
	public int updateUserById(User user) {
		String sql = "UPDATE `users` SET `username`=?,`pasword`=?,`phone_no`=?,`address`=? WHERE `id`=?;";
		return super.update(sql, user.getUsername(), user.getPasword(), user.getPhoneNo(), user.getAddress(),
				user.getId());
	}

	@Override
	public User get(int id) {
		String sql = "SELECT `id`,`username`,`pasword`,`phone_no` phoneNo,`address`,`reg_date` regDate FROM `users` WHERE `id`=?;";
		return super.get(sql, id);
	}

	@Override
	public User get(Connection conn, int id) {
		String sql = "SELECT `id`,`username`,`pasword`,`phone_no` phoneNo,`address`,`reg_date` regDate FROM `users` WHERE `id`=?;";
		return super.get(conn, sql, id);
	}

	@Override
	public List<User> getListAll() {
		String sql = "SELECT `id`,`username`,`pasword`,`phone_no` phoneNo,`address`,`reg_date` regDate FROM `users`;";
		return super.getList(sql);
	}

	@Override
	public long getCountByName(String username) {
		String sql = "SELECT COUNT(`id`) FROM `users` WHERE `username`=?;";
		return (long) super.getValue(sql, username);
	}

	@Override
	public List<User> query(String username, String address, String phoneNo) {
		String sql = "SELECT `id`,`username`,`pasword`,`phone_no` phoneNo,`address`,`reg_date` regDate FROM `users` WHERE 1=1";
		if(username!=null && !"".equals(username)) {
			sql = sql + " AND username like '%"+username+"%'"; 
		}
		if(address!=null && !"".equals(address)) {
			sql = sql + " AND address like '%"+address+"%'";
		}
		if(phoneNo!=null && !"".equals(phoneNo)) {
			sql = sql + " AND phone_no like '%"+phoneNo+"%'";
		}
		System.out.println(sql);
		return super.getList(sql);
	}

	@Override
	public User getUserByUp(String username, String pasword) {
		String sql = "SELECT `id`,`username`,`pasword`,`phone_no` phoneNo,`address`,`reg_date` regDate FROM users WHERE username=? AND pasword=?";
		return super.get(sql, username,pasword);
	}

}
