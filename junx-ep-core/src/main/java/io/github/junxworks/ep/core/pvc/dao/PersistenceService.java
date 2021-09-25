package io.github.junxworks.ep.core.pvc.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface PersistenceService {
	public void setDataSource(DataSource ds);

	public int getCurrentPVByModuleName(String moduleName) throws SQLException;

	public int setPV(String moduleName, int version) throws SQLException;

	public void executeSql(String sql) throws SQLException;
}
