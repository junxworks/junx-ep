package io.github.junxworks.ep.core.pvc.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import io.github.junxworks.ep.core.ds.DynamicDataSource;

public class PersistenceServiceImpl implements PersistenceService {
	
	private static final String CREATE_TABLE_PROFILE="CREATE TABLE IF NOT EXISTS `ep_s_profile` (`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',`name` varchar(200) DEFAULT NULL COMMENT '配置项名称',`value` varchar(200) DEFAULT NULL COMMENT '配置值',`remark` varchar(100) DEFAULT NULL COMMENT '备注',PRIMARY KEY (`id`),UNIQUE KEY `idx_uniq_name` (`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='EP系统简述';";
	
	private static final String PV_PREFFIX = "pv_";

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int getCurrentPVByModuleName(String moduleName) throws SQLException {
		DataSource primary = getPrimaryDataSource();
		String modulePvc = getPropertyName(moduleName);
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement("select * from ep_s_profile where name=?"); Statement st = conn.createStatement();) {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet tableRS = dbmd.getTables(null, null, "ep_s_profile", null);
			if (tableRS.next()) {
				pst.setString(1, modulePvc);
				try (ResultSet rs = pst.executeQuery();) {
					if (rs.next()) {
						return Integer.valueOf(rs.getString("value"));
					}
				}
				return -1;
			} else {
				st.execute(CREATE_TABLE_PROFILE);
				return -1;
			}

		}
	}

	private String getPropertyName(String moduleName) {
		return PV_PREFFIX + moduleName;
	}

	private DataSource getPrimaryDataSource() {
		DataSource primary = null;
		if (dataSource instanceof DynamicDataSource) {
			primary = ((DynamicDataSource) dataSource).getPrimaryDataSource();
		} else {
			primary = dataSource;
		}
		return primary;
	}

	@Override
	public int setPV(String moduleName, int version) throws SQLException {
		boolean exists = version != 0;
		DataSource primary = getPrimaryDataSource();
		String modulePvc = getPropertyName(moduleName);
		String value = String.valueOf(version);
		String sql = null;
		if (exists) {
			sql = "UPDATE ep_s_profile set value=? where name=?";
		} else {
			sql = "INSERT INTO ep_s_profile (value,name, remark) VALUES (?, ?,'EP持久化版本控制')";
		}
		try (Connection conn = primary.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, value);
			pst.setString(2, modulePvc);
			return pst.executeUpdate();
		}
	}

	@Override
	public void executeSql(String sql) throws SQLException {
		DataSource primary = getPrimaryDataSource();
		try (Connection conn = primary.getConnection(); Statement st = conn.createStatement();) {
			st.execute(sql);
		}
	}

}
