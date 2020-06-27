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

public class EPShiroService {

	private static final String QUERY_USER = "select s.*,o.orgName,o.orgType from s_user s left join s_org o on s.orgNo=o.orgNo and o.status=0 where s.username=? and s.password=? and s.status=0";

	private static final String QUERY_USER_AUTHORIZATIONS = "select distinct m.mark from s_user u,s_user_role r,s_role_menu rm,s_menu m where u.id=? and u.status=0 and u.id=r.userId and r.roleId=rm.roleId and rm.menuId=m.id and m.status=0 and m.mark != '' and m.mark is not null";

	private static final String QUERY_ALL_AUTHORIZATIONS = "select distinct m.mark from s_menu m where m.status=0 and m.mark != '' and m.mark is not null";

	private static final String QUERY_USER_ROLES = "select distinct r.roleTag from s_role r,s_user u,s_user_role ur where u.id=? and u.status=0 and u.id=ur.userId and ur.roleId=r.id and r.status=0 and r.roleTag != '' and r.roleTag is not null";

	private static final String QUERY_ALL_ROLES = "select distinct r.roleTag from s_role r where r.status=0 and r.roleTag != '' and r.roleTag is not null";

	@Autowired
	private DynamicDataSource dynamicDataSource;

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

	private UserModel createUserModelFromRes(ResultSet rs) throws SQLException {
		if (rs != null) {
			UserModel model = new UserModel();
			model.setId(rs.getLong("id"));
			model.setEmail(rs.getString("email"));
			model.setIdCard(rs.getString("idCard"));
			model.setMobile(rs.getString("mobile"));
			model.setNick(rs.getString("name"));
			model.setName(rs.getString("name"));
			model.setUsername(rs.getString("username"));
			model.setOrgNo(rs.getString("orgNo"));
			model.setOrgName(rs.getString("orgName"));
			model.setOrgType(rs.getString("orgType"));
			model.setStatus(rs.getInt("status"));
			return model;
		}
		return null;
	}

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
					roles.add(rs.getString("roleTag"));
				}
			}
		}
		return roles;
	}
}
