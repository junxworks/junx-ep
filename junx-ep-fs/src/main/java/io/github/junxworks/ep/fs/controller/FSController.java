/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  FileServiceController.java   
 * @Package com.yrxd.filesvc.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-9 15:11:25   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.fs.driver.FSConfig;
import io.github.junxworks.ep.fs.driver.FileRepository;
import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;
import io.github.junxworks.ep.fs.service.impl.FileServiceImpl;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/ep/fs")
public class FSController {
	private static final Logger logger = LoggerFactory.getLogger(FSController.class);

	private static final String ATTACHMENT = "file";

	private static final String FILE_GROUP = "group";

	private static final String FILE_GROUP_DEFAULT = "default";

	private static final String ORG_NO = "orgNo";

	/** 缩略图前缀. */
	private static final String THUMB_GROUP = "thumbnail";

	@Autowired
	private FileRepository fr;

	@Autowired
	private FileServiceImpl fileService;

	@Autowired
	private FSConfig fsConfig;

	private static final String DEFAULT_TYPE = "application/octet-stream";

	/**
	 * 上传一个文件，并且返回文件存储的原数据
	 *
	 * @param file the file
	 * @return the result
	 */
	@PostMapping(path = "/files", consumes = "multipart/form-data", produces = "application/json; charset=UTF-8")
	public Result multiUpload(MultipartHttpServletRequest multiReq) {
		MultipartFile file = multiReq.getFile(ATTACHMENT);
		if (file == null) {
			return Result.error("上传文件为空");
		}
		String orgNo = multiReq.getParameter(ORG_NO);
		String fileGroup = multiReq.getParameter(FILE_GROUP);
		if (StringUtils.isBlank(fileGroup)) {
			fileGroup = FILE_GROUP_DEFAULT;
		}
		String storageId = null;
		try (InputStream in = file.getInputStream()) {
			storageId = fr.storeFile(in);
		} catch (Exception e) {
			logger.error("上传文件异常", e);
			return Result.error(ExceptionUtils.getCauseMessage(e));
		}
		SFile sysFile = new SFile();
		sysFile.setStorageId(storageId);
		sysFile.setStorageDriver(fr.getClass().getCanonicalName());
		sysFile.setCreateTime(new Date());
		String fileName = file.getOriginalFilename();
		String extName = null;
		int idx = fileName.lastIndexOf(".");
		if (idx > 0) {
			extName = fileName.substring(idx + 1);
			sysFile.setFileExtension(extName);
		}
		sysFile.setFileName(file.getName());
		sysFile.setOraginalName(file.getOriginalFilename());
		sysFile.setFileSize((int) file.getSize());
		sysFile.setOrgNo(orgNo);
		sysFile.setFileGroup(fileGroup);
		fileService.inser(sysFile);
		return Result.ok(sysFile);
	}

	/**
	 * 以附件方式下载上传文件.
	 *
	 * @param id the id
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/files/{id}/attachment")
	public void downloadAttachment(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
		download(id, "attachment", response);
	}

	/**
	 * 以浏览器直接打开方式下载上传文件.
	 *
	 * @param id the id
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/files/{id}")
	public void downloadInline(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
		download(id, "inline", response);
	}

	private void download(@PathVariable("id") String id, String type, HttpServletResponse response) throws IOException {
		SFile sysFile = fileService.findById(id);
		if (sysFile == null) {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
			response.getWriter().write(Result.error("File info does not exist.").toJson());
			return;
		}
		try (OutputStream outStream = new BufferedOutputStream(response.getOutputStream());) {
			response.reset();
			response.addHeader("Content-Disposition", type + ";filename=attachment." + sysFile.getFileExtension());
			String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(sysFile.getFileExtension()), DEFAULT_TYPE);
			response.setContentType(contentType);
			response.addHeader("Content-Length", "" + sysFile.getFileSize());
			fr.fetchFileIntoStream(sysFile.getStorageId(), outStream);
			outStream.flush();
		} catch (Exception e) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(Result.error(ExceptionUtils.getCauseMessage(e)).toJson());
		}
	}

	/**
	 * 获取上传文件的原数据
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/files/{id}/metadata")
	public Result info(@PathVariable String id) {
		SFile fi = fileService.findById(id);
		if (fi != null) {
			return Result.ok(fi);
		}
		return Result.warn("File metadata does not exist.");
	}

	/**
	 * 以浏览器直接打开的方式下载缩略图
	 *
	 * @param fileId the file id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/files/{id}/thumbnail/{width}/{height}")
	public void imageThumbnail(@PathVariable String id, @PathVariable Integer width, @PathVariable Integer height, HttpServletResponse response) throws Exception {
		imageThumbnail(id, width, height, "inline", response);
	}

	/**
	 * 以附件的方式下载缩略图
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/files/{id}/thumbnail/{width}/{height}/attachment")
	public void imageThumbnailAttachment(@PathVariable String id, @PathVariable Integer width, @PathVariable Integer height, HttpServletResponse response) throws Exception {
		imageThumbnail(id, width, height, "attachment", response);
	}

	private void imageThumbnail(String id, Integer width, Integer height, String type, HttpServletResponse response) throws Exception {
		SFile fi = fileService.findById(id);
		if (fi == null) {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
			response.getWriter().print(Result.error("File does't exists.").toJson());
			return;
		}
		if (!readFromCache(fi, width, height, type, response))
			writeThumbnail(fi, fr.fetchFileAsStream(fi.getStorageId()), width, height, type, response);
	}

	private boolean readFromCache(SFile file, int width, int height, String type, HttpServletResponse response) throws Exception {
		SFileThumb t = fileService.findThumbByIdAndSize(file.getId(), width, height);
		if (t == null) {
			return false;
		}
		byte[] data = fr.fetchFileBytes(t.getStorageId());
		response.reset();
		response.addHeader("Content-Disposition", type + ";filename=thumbnail." + t.getFileExtension());
		String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(t.getFileExtension()), DEFAULT_TYPE);
		response.setContentType(contentType);
		response.addHeader("Content-Length", "" + data.length);
		try {
			response.getOutputStream().write(data);
		} catch (Throwable e) {
			logger.error("Exception occurred when response thumbnail data.\"{}\"", ExceptionUtils.getCause(e));
		}
		return true;
	}

	private void writeThumbnail(SFile file, InputStream is, int width, int height, String type, HttpServletResponse response) throws Exception {
		response.reset();
		response.addHeader("Content-Disposition", type + ";filename=thumbnail." + file.getFileExtension());
		String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(file.getFileExtension()), DEFAULT_TYPE);
		response.setContentType(contentType);
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			Thumbnails.of(is).width(width).height(height).toOutputStream(os);
			response.addHeader("Content-Length", "" + os.size());
			byte[] data = os.toByteArray();
			try {
				response.getOutputStream().write(data);
			} catch (Throwable e) {
				logger.error("Exception occurred when response thumbnail data.\"{}\"", ExceptionUtils.getCause(e));
			} finally {
				String token = fr.storeFile(data);
				SFileThumb t = new SFileThumb();
				t.setCreateTime(new Date());
				t.setFileExtension(file.getFileExtension());
				t.setFileId(file.getId());
				t.setFileSize(Long.valueOf(data.length));
				t.setHeight(height);
				t.setWidth(width);
				t.setStorageDriver(fr.getClass().getCanonicalName());
				t.setStorageId(token);
				fileService.saveSysFileThumb(t);
			}
		} finally {
			is.close();
		}
	}
}
