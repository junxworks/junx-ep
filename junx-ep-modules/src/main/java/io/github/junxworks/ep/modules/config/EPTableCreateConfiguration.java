package io.github.junxworks.ep.modules.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.core.svc.InitDBTableService;
import io.github.junxworks.ep.core.utils.SpringContextUtils;
import io.github.junxworks.junx.core.lang.Initializable;

@Configuration
@Import({ SpringContextUtils.class })
public class EPTableCreateConfiguration extends TableCreateConfiguration {
	public static final List<String> tables = Lists.newArrayList("s_role", "s_user", "s_org", "s_user_role", "s_menu", "s_op_log", "s_role_menu", "s_dict", "s_sqls");

	@Bean(name = "EPTableDBInit", initMethod = "initialize", destroyMethod = "destroy")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Initializable EPTableDBInit(final JdbcTemplate jdbcTemplate) throws IOException {
		return new Initializable() {
			@Override
			public void initialize() throws Exception {
				for (String table : tables) {
					InitDBTableService service = createTableValidate(table, table);
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
