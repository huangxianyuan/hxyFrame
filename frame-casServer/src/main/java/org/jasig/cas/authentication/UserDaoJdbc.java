package org.jasig.cas.authentication;

import org.jasig.cas.utils.ShiroUtils;
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
			User user = getByUsername(username);
			String newPassWord = ShiroUtils.EncodeSalt(password,user.getSalt());
			if(!newPassWord.equals(user.getPassWord())){
				return false;
			}
		}catch(EmptyResultDataAccessException e){
			return false;
		}
		return true;
	}

	/**
	 * 根据登陆帐号获取用户信息
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
		user.setSalt(rs.getString("salt"));
		user.setPassWord(rs.getString("pass_word"));
		return user;
	}
}