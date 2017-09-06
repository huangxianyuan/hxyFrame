package org.jasig.cas.authentication;

import org.jasig.cas.utils.MD5;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoJdbc {
	private static final String SQL_USER_VERIFY = "SELECT COUNT(*) FROM sys_user WHERE login_name=? AND pass_word=?";
	private static final String SQL_USER_GET = "SELECT * FROM sys_user WHERE login_name=?";

	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 验证用户名和密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean verifyAccount(String username, String password){
		try{
			//md5加密
			password = MD5.MD5Encode(password);
			return 1==this.jdbcTemplate.queryForObject(SQL_USER_VERIFY, new Object[]{username, password}, Integer.class);
		}catch(EmptyResultDataAccessException e){
			return false;
		}
	}

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	public User getByUsername(String username){
		try{
			return (User)this.jdbcTemplate.queryForObject(SQL_USER_GET, new Object[]{username}, new UserRowMapper());
		}catch(EmptyResultDataAccessException e){
			return new User();
		}
	}
}


class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int index) throws SQLException {
		User user = new User();
		user.setLoginName(rs.getString("login_name"));
		user.setId(rs.getString("id"));
		return user;
	}
}