package io.github.junxworks.ep.codegen.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.stringtemplate.v4.ST;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeGenerator.
 */
public class CodeGenerator {

	/**
	 * 生成Pojo类.
	 *
	 * @param context the context
	 * @throws Exception the exception
	 */
	public static void generate(final GenerationContext context) throws Exception {
		if (context.getFileDir() == null) {
			throw new BaseRuntimeException("File directory can not be empty");
		}
		DatabaseElement de = context.getDatabase();
		if (GenerateHelper.isEmpty(de.getType()) || GenerateHelper.isEmpty(de.getUrl()) || GenerateHelper.isEmpty(de.getUsername()) || GenerateHelper.isEmpty(de.getPassword())) {
			throw new BaseRuntimeException("Please finish database configuration first");
		}
		DataBase db = DatabaseFactory.creatDataBase(de);
		Table table = db.describeTable(de.getSchema(), context.getTableName());
		if (table == null) {
			throw new BaseRuntimeException("Table \"" + context.getTableName() + "\" can not be found");
		}
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setTemplateLoader(new TemplateLoader() {

			public void closeTemplateSource(Object templateSource) throws IOException {
				((InputStream) templateSource).close();
			}

			public Object findTemplateSource(String name) throws IOException {
				return new ByteArrayInputStream(context.getTemplate().getBytes());
			}

			public long getLastModified(Object templateSource) {
				return 0;
			}

			public Reader getReader(Object templateSource, String encoding) throws IOException {
				return new InputStreamReader((InputStream) templateSource, encoding);
			}
		});
		cfg.setEncoding(Locale.CHINA, "UTF-8");
		Template template = cfg.getTemplate("");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("table", table);
		root.put("attr", context.getAttr());
		ST fn = new ST(context.getFileNameExp(), '{', '}');
		if (context.getAttr() != null) {
			context.getAttr().entrySet().forEach(en -> {
				fn.add(en.getKey(), en.getValue());
			});
		}
		fn.add("table", table);
		String fileName = fn.render();
		try (Writer out = new OutputStreamWriter(new FileOutputStream(new File(context.getFileDir(), fileName)), "UTF-8")) {
			template.process(root, out);
			out.flush();
		}
	}

	/**
	 * 获取所有表名.
	 *
	 * @param de the de
	 * @param tableNamePrefix the table name prefix
	 * @return the list
	 * @throws Exception the exception
	 */
	public static List<Table> queryTableList(DatabaseElement de, String tableNamePrefix) throws Exception {
		if (GenerateHelper.isEmpty(de.getType()) || GenerateHelper.isEmpty(de.getUrl()) || GenerateHelper.isEmpty(de.getUsername()) || GenerateHelper.isEmpty(de.getPassword())) {
			throw new Exception("Please finish database configuration first");
		}
		DataBase db = DatabaseFactory.creatDataBase(de);
		List<Table> tName = db.queryTableList(de.getSchema(), tableNamePrefix);
		return tName;
	}

}
