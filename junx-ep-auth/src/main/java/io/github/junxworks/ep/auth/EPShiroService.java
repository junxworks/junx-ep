/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPShiroService.java   
 * @Package io.github.junxworks.ep.auth   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:41   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.core.ds.DynamicDataSource;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPShiroService
 * @author: Michael
 * @date:   2020-7-19 12:18:41
 * @since:  v1.0
 */
public class EPShiroService {

	/** 常量 QUERY_USER. */
	private static final String QUERY_USER = "select s.*,o.org_name,o.org_type from ep_s_user s left join ep_s_org o on s.org_no=o.org_no where s.username=? and s.password=?";

	private static final String LOCK_USER = "update ep_s_user set status=2 where username=?";

	/** 常量 QUERY_USER_AUTHORIZATIONS. */
	private static final String QUERY_USER_AUTHORIZATIONS = "select distinct m.mark from ep_s_user u,ep_s_user_role r,ep_s_role_menu rm,ep_s_menu m where u.id=? and u.status=0 and u.id=r.user_id and r.role_id=rm.role_id and rm.menu_id=m.id and m.status=0 and m.mark != '' and m.mark is not null";

	/** 常量 QUERY_ALL_AUTHORIZATIONS. */
	private static final String QUERY_ALL_AUTHORIZATIONS = "select distinct m.mark from ep_s_menu m where m.status=0 and m.mark != '' and m.mark is not null";

	/** 常量 QUERY_USER_ROLES. */
	private static final String QUERY_USER_ROLES = "select distinct r.role_tag from ep_s_role r,ep_s_user u,ep_s_user_role ur where u.id=? and u.status=0 and u.id=ur.user_id and ur.role_id=r.id and r.status=0 and r.role_tag != '' and r.role_tag is not null";

	/** 常量 QUERY_ALL_ROLES. */
	private static final String QUERY_ALL_ROLES = "select distinct r.role_tag from ep_s_role r where r.status=0 and r.role_tag != '' and r.role_tag is not null";

	/** dynamic data source. */
	@Autowired
	private DynamicDataSource dynamicDataSource;

	/**
	 * 返回 user 属性.
	 *
	 * @param username the username
	 * @param password the password
	 * @return user 属性
	 * @throws SQLException the SQL exception
	 */
	public UserModel getUser(String username, String password) throws SQLException {
		//所有验证均从主库中取数据
		DataSource primary = dynamicDataSource.getPrimaryDataSource();
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement(QUERY_USER);) {
			pst.setString(1, username);
			pst.setString(2, password);
			try (ResultSet rs = pst.executeQuery();) {
				if (rs.next()) {
					return createUserModelFromRes(rs);
				}
			}
			return null;
		}
	}

	/**
	 * Lock user.
	 *
	 * @param username the username
	 * @throws SQLException the SQL exception
	 */
	public void lockUser(String username) throws SQLException {
		//所有验证均从主库中取数据
		DataSource primary = dynamicDataSource.getPrimaryDataSource();
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement(LOCK_USER);) {
			pst.setString(1, username);
			pst.execute();
		}
	}

	/**
	 * Creates the user model from res.
	 *
	 * @param rs the rs
	 * @return the user model
	 * @throws SQLException the SQL exception
	 */
	private UserModel createUserModelFromRes(ResultSet rs) throws SQLException {
		if (rs != null) {
			UserModel model = new UserModel();
			model.setId(rs.getLong("id"));
			model.setEmail(rs.getString("email"));
			model.setIdCard(rs.getString("id_card"));
			model.setMobile(rs.getString("mobile"));
			model.setNick(rs.getString("name"));
			model.setName(rs.getString("name"));
			model.setUsername(rs.getString("username"));
			model.setOrgNo(rs.getString("org_no"));
			model.setOrgName(rs.getString("org_name"));
			model.setOrgType(rs.getString("org_type"));
			model.setStatus(rs.getInt("status"));
			return model;
		}
		return null;
	}

	/**
	 * 返回 user authorizations 属性.
	 *
	 * @param userId the user id
	 * @return user authorizations 属性
	 * @throws SQLException the SQL exception
	 */
	public List<String> getUserAuthorizations(Long userId) throws SQLException {
		boolean isAdmin = userId != null && userId == -1;
		//所有验证均从主库中取数据
		DataSource primary = dynamicDataSource.getPrimaryDataSource();
		String sql = QUERY_USER_AUTHORIZATIONS;
		if (isAdmin) {
			sql = QUERY_ALL_AUTHORIZATIONS;
		}
		List<String> authorizations = Lists.newArrayList();
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			if (!isAdmin) {
				pst.setLong(1, userId);
			}
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					authorizations.add(rs.getString("mark"));
				}
			}
		}
		return authorizations;
	}

	/**
	 * 返回 user roles 属性.
	 *
	 * @param userId the user id
	 * @return user roles 属性
	 * @throws SQLException the SQL exception
	 */
	public List<String> getUserRoles(Long userId) throws SQLException {
		boolean isAdmin = userId != null && userId == -1;
		//所有验证均从主库中取数据
		DataSource primary = dynamicDataSource.getPrimaryDataSource();
		String sql = QUERY_USER_ROLES;
		if (isAdmin) {
			sql = QUERY_ALL_ROLES;
		}
		List<String> roles = Lists.newArrayList();
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			if (!isAdmin) {
				pst.setLong(1, userId);
			}
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					roles.add(rs.getString("role_tag"));
				}
			}
		}
		return roles;
	}
}
