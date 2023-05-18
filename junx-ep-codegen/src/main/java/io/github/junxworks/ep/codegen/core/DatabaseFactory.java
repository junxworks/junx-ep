package io.github.junxworks.ep.codegen.core;

public class DatabaseFactory {
	/**
	 * 创建数据库操作对象
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static DataBase creatDataBase(DatabaseElement de) throws Exception {
		if (de.getType().toLowerCase().contains("mysql")) {
			return new MySqlDatabase(de);
		}
		return null;
	}

}
