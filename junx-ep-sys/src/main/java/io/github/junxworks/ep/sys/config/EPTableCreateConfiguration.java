/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  EPTableCreateConfiguration.java   
 * @Package io.github.junxworks.ep.sys.config   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:17:48   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.sys.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.core.svc.TableInitComponent;
import io.github.junxworks.ep.core.svc.TableInitializer;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.junx.core.lang.Initializable;

/**
 * {类的详细说明}.
 *
 * @ClassName:  EPTableCreateConfiguration
 * @author: Michael
 * @date:   2020-7-19 12:17:48
 * @since:  v1.0
 */
@Configuration
@Import({ SpringContextUtils.class })
public class EPTableCreateConfiguration extends TableInitComponent {

	/** 常量 tables. */
	public static final List<String> tables = Lists.newArrayList("s_role", "s_user", "s_org", "s_user_role", "s_menu", "s_op_log", "s_role_menu", "s_dict", "s_sqls", "s_profile");

	/** 常量 INIT_PATH. */
	private static final String INIT_PATH = "/io/github/junxworks/ep/sys/init";

	/**
	 * EP table DB init.
	 *
	 * @param jdbcTemplate the jdbc template
	 * @return the initializable
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Bean(name = "EPTableDBInit", initMethod = "initialize", destroyMethod = "destroy")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Initializable EPTableDBInit(final JdbcTemplate jdbcTemplate) throws IOException {
		return new Initializable() {
			@Override
			public void initialize() throws Exception {
				for (String table : tables) {
					TableInitializer service = createTableValidate(INIT_PATH, table, table);
					service.setJdbcTemplate(jdbcTemplate);
					service.start();
				}
			}

			@Override
			public void destroy() throws Exception {
			}
		};

	}
}
