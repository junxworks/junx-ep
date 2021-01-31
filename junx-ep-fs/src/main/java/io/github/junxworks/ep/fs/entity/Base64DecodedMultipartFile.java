/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  Base64DecodedMultipartFile.java   
 * @Package io.github.junxworks.ep.fs.entity   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-31 17:02:10   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * {类的详细说明}.
 *
 * @ClassName:  Base64DecodedMultipartFile
 * @author: Michael
 * @date:   2021-1-31 17:02:10
 * @since:  v1.0
 */
public class Base64DecodedMultipartFile implements MultipartFile {

	/** img content. */
	private final byte[] imgContent;

	/** header. */
	private final String header;

	/**
	 * 构造一个新的 base 64 decoded multipart file 对象.
	 *
	 * @param imgContent the img content
	 * @param header the header
	 */
	public Base64DecodedMultipartFile(byte[] imgContent, String header) {
		this.imgContent = imgContent;
		this.header = header.split(";")[0];
	}

	@Override
	public String getName() {
		return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
	}

	@Override
	public String getOriginalFilename() {
		return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
	}

	@Override
	public String getContentType() {
		return header.split(":")[1];
	}

	@Override
	public boolean isEmpty() {
		return imgContent == null || imgContent.length == 0;
	}

	@Override
	public long getSize() {
		return imgContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(imgContent);
	}

	/**
	 * Transfer to.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalStateException the illegal state exception
	 */
	@Override
	public void transferTo(File file) throws IOException, IllegalStateException {
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(imgContent);
		}
	}
}
