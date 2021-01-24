/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  LocalFileSystemDriver.java   
 * @Package io.github.junxworks.ep.fs.driver   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.driver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public class LocalFileSystemDriver extends AbstractFileRepository {
	private LocalFSConfig config;

	public LocalFSConfig getConfig() {
		return config;
	}

	public void setConfig(LocalFSConfig config) {
		this.config = config;
	}

	@Override
	public String storeFile(File file) throws StoreFailedException {
		FileObj destFile = destFile();
		try {
			FileUtils.copyFile(file, destFile.getFile());
		} catch (Exception e) {
			throw new StoreFailedException("Store file failed.", e);
		}
		return destFile.getToken();
	}

	@Override
	public String storeFile(byte[] bytes) throws StoreFailedException {
		FileObj destFile = destFile();
		try (ByteArrayInputStream bi = new ByteArrayInputStream(bytes)) {
			storeFile(bi);
		} catch (Exception e) {
			throw new StoreFailedException("Store file failed.", e);
		}
		return destFile.getToken();
	}

	@Override
	public String storeFile(InputStream inputStream) throws StoreFailedException {
		FileObj destFile = destFile();
		try {
			FileUtils.copyInputStreamToFile(inputStream, destFile.getFile());
		} catch (Exception e) {
			throw new StoreFailedException("Store file failed.", e);
		}
		return destFile.getToken();
	}

	private FileObj destFile() {
		FileObj fo = new FileObj();
		String token = createToken();
		File destFile = new File(getFilePath(token));
		fo.setToken(token);
		fo.setFile(destFile);
		return fo;
	}

	private String getFilePath(String token) {
		return config.getDataDir() + File.separator + token;
	}

	@Override
	protected void doStart() throws Throwable {
		File file = new File(config.getDataDir());
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	@Override
	protected void doStop() throws Throwable {

	}

	@Override
	public byte[] fetchFileBytes(String fileToken) throws FetchFailedException {
		try {
			File file = new File(getFilePath(fileToken));
			if (file.exists()) {
				return FileUtils.readFileToByteArray(file);
			} else {
				throw new FileNotFoundException(fileToken);
			}
		} catch (Exception e) {
			throw new FetchFailedException("Fetch file failed.", e);
		}
	}

	@Override
	public void fetchFileIntoStream(String fileToken, OutputStream output) throws FetchFailedException {
		try {
			File file = new File(getFilePath(fileToken));
			if (file.exists()) {
				try (FileInputStream in = new FileInputStream(file);) {
					intputStreamToOutputStream(in, output);
				}
			} else {
				throw new FileNotFoundException(fileToken);
			}
		} catch (Exception e) {
			throw new FetchFailedException("Fetch file failed.", e);
		}
	}

	@Override
	public InputStream fetchFileAsStream(String fileToken) throws FetchFailedException {
		try {
			File file = new File(getFilePath(fileToken));
			if (file.exists()) {
				return new FileInputStream(file);
			} else {
				throw new FileNotFoundException(fileToken);
			}
		} catch (Exception e) {
			throw new FetchFailedException("Fetch file failed.", e);
		}
	}

	private static final class FileObj {
		private String token;

		private File file;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

	}

}
