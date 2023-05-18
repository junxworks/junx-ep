package io.github.junxworks.ep.fs.driver;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.junxworks.ep.fs.config.AliyunOssConfig;
import io.github.junxworks.ep.fs.config.FSConfig;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.lang.Initializable;
import io.github.junxworks.junx.core.util.StringUtils;

public class FileRepositoryFactory implements Initializable {

	private Map<String, FileRepository> repositories = Maps.newHashMap();

	public FileRepository getRepository(String driver, FSConfig config) throws Exception {
		FileRepository resp = repositories.get(driver);
		if (resp != null) {
			return resp;
		}
		synchronized (FileRepositoryFactory.class) {
			resp = repositories.get(driver);
			if (resp != null) {
				return resp;
			}
			switch (driver) {
				case "io.github.junxworks.ep.fs.driver.OssRepositoryDriver":
					resp = new OssRepositoryDriver();
					AliyunOssConfig c = config.getOss();
					if (StringUtils.isNull(c.getAccessKeyId()) || StringUtils.isNull(c.getAccessKeySecret())) {
						throw new BaseRuntimeException("OSS configuration is missing.");
					}
					((OssRepositoryDriver) resp).setConfig(c);
					resp.start();
					break;
				case "io.github.junxworks.ep.fs.driver.LocalFileSystemDriver":
					resp = new LocalFileSystemDriver();
					((LocalFileSystemDriver) resp).setConfig(config.getLocal());
					break;
				default:
			}
			if (resp == null) {
				throw new BaseRuntimeException("Unkown file repository driver.");
			}
			repositories.put(driver, resp);
		}
		return resp;
	}

	@Override
	public void initialize() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws Exception {
		if (!repositories.isEmpty()) {
			repositories.values().forEach(v -> {
				try {
					v.stop();
				} catch (Throwable t) {
				}
			});
		}
	}
}
