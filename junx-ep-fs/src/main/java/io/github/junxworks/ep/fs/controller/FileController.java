/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileController.java   
 * @Package io.github.junxworks.ep.fs.controller   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-2-19 16:09:15   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
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
import java.util.List;

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
import io.github.junxworks.ep.core.fs.FsMode;
import io.github.junxworks.ep.fs.config.FSConfig;
import io.github.junxworks.ep.fs.constants.ContentType;
import io.github.junxworks.ep.fs.driver.FileRepository;
import io.github.junxworks.ep.fs.driver.FileRepositoryFactory;
import io.github.junxworks.ep.fs.entity.EpSFile;
import io.github.junxworks.ep.fs.entity.EpSFileThumb;
import io.github.junxworks.ep.fs.service.impl.FileServiceImpl;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.ExceptionUtils;
import io.github.junxworks.junx.core.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;

/**
 * 文件处理controller
 *
 * @ClassName:  FileController
 * @author: Michael
 * @date:   2021-1-31 18:00:37
 * @since:  v1.0
 */
@RestController
@RequestMapping("/ep/fs/files")
public class FileController {

	/** 常量 logger. */
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	/** 常量 ATTACHMENT. */
	private static final String FILE = "file";

	private static final String FILENAME = "filename";

	/** 常量 FILE_GROUP. */
	private static final String FILE_GROUP = "group";

	/** 常量 ORG_NO. */
	private static final String ORG_NO = "orgNo";

	private static final String MODE = "mode";

	/** fr. */
	@Autowired
	private FileRepository defaultFr;

	/** file service. */
	@Autowired
	private FileServiceImpl fileService;

	/** fs config. */
	@Autowired
	private FSConfig fsConfig;

	/** 常量 DEFAULT_TYPE. */
	private static final String DEFAULT_TYPE = "application/octet-stream";

	@Autowired
	private FileRepositoryFactory fileRepositoryFactory;

	/**
	 * 文件上传，支持多文件同时上传，返回的元数据数组顺序同文件传入顺序
	 *
	 * @param multiReq the multi req
	 * @return the result
	 */
	@PostMapping(consumes = "multipart/form-data", produces = "application/json; charset=UTF-8")
	public Result multiUpload(MultipartHttpServletRequest multiReq) {
		List<MultipartFile> fs = multiReq.getFiles(FILE);
		if (fs == null || fs.isEmpty()) {
			return Result.error("Empty file.");
		}
		if (fs.size() > 1) {
			return Result.error("Current now just support 1 file.");
		}
		MultipartFile file = fs.get(0);
		if (file == null) {
			return Result.error("Empty file.");
		}
		String orgNo = multiReq.getParameter(ORG_NO);
		String fileGroup = multiReq.getParameter(FILE_GROUP);
		String mode = multiReq.getParameter(MODE);
		String fileName = multiReq.getParameter(FILENAME);
		String storageId = null;
		FileRepository fr = null;
		try (InputStream in = file.getInputStream()) {
			if (StringUtils.isNull(mode)) {
				fr = this.defaultFr;
			} else {
				FsMode repo = FsMode.getEnum(mode);
				if (repo == null) {
					throw new BaseRuntimeException("未定义的存储驱动类型：" + mode);
				} else {
					fr = fileRepositoryFactory.getRepository(repo.getDriver(), fsConfig);
				}
			}
			storageId = fr.storeFile(in);
		} catch (Exception e) {
			logger.error("Store file failed.", e);
			return Result.error(ExceptionUtils.getCauseMessage(e));
		}
		EpSFile sysFile = new EpSFile();
		sysFile.setStorageId(storageId);
		sysFile.setStorageDriver(fr.getClass().getCanonicalName());
		sysFile.setCreateTime(new Date());
		if (StringUtils.isNull(fileName)) {
			fileName = file.getOriginalFilename();
		}
		String extName = null;
		String originalName = file.getOriginalFilename();
		int idx = originalName.lastIndexOf(".");
		if (idx > 0) {
			extName = originalName.substring(idx + 1);
			sysFile.setFileExt(extName);
		}
		sysFile.setFileName(fileName);
		sysFile.setOraginalName(originalName);
		sysFile.setFileSize((int) file.getSize());
		sysFile.setOrgNo(orgNo);
		sysFile.setFileGroup(fileGroup);
		fileService.inser(sysFile);
		return Result.ok(sysFile);
	}

	/**
	 * Download attachment.
	 *
	 * @param id the id
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/attachments")
	public void downloadAttachment(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		download(id, ContentType.ATTACHMENT.getValue(), request, response);
	}

	/**
	 * Download inline.
	 *
	 * @param id the id
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}")
	public void downloadInline(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		download(id, ContentType.INLINE.getValue(), request, response);
	}

	/**
	 * Download.
	 *
	 * @param id the id
	 * @param type the type
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void download(@PathVariable("id") Long id, String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EpSFile sysFile = fileService.findById(id);
		if (sysFile == null) {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
			response.getWriter().write(Result.error("File info does not exist.").toJson());
			return;
		}
		try (OutputStream outStream = new BufferedOutputStream(response.getOutputStream());) {
			response.reset();
			String userAgent = request.getHeader("User-Agent");
			String formFileName = StringUtils.isEmpty(sysFile.getOraginalName()) ? "attachment" : sysFile.getOraginalName();
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
			} else {
				// 非IE浏览器的处理：
				formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.addHeader("Content-Disposition", type + ";filename=" + formFileName);
			String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(sysFile.getFileExt()), DEFAULT_TYPE);
			response.setContentType(contentType);
			if (fsConfig.isResponseHeaderWithContentLength()) {
				response.addHeader("Content-Length", "" + sysFile.getFileSize());
			}
			FileRepository repo = fileRepositoryFactory.getRepository(sysFile.getStorageDriver(), fsConfig);
			repo.fetchFileIntoStream(sysFile.getStorageId(), outStream);
			outStream.flush();
		} catch (Exception e) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(Result.error(ExceptionUtils.getCauseMessage(e)).toJson());
		}
	}

	/**
	 * Info.
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/{id}/metadata")
	public Result info(@PathVariable Long id) {
		EpSFile fi = fileService.findById(id);
		if (fi != null) {
			return Result.ok(fi);
		}
		return Result.warn("File metadata does not exist.");
	}

	/**
	 * Image thumbnail.
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/thumbnails/{width}/{height}")
	public void imageThumbnail(@PathVariable Long id, @PathVariable Integer width, @PathVariable Integer height, HttpServletResponse response) throws Exception {
		imageThumbnail(id, width, height, "inline", response);
	}

	/**
	 * Image thumbnail attachment.
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/thumbnails/{width}/{height}/attachments")
	public void imageThumbnailAttachment(@PathVariable Long id, @PathVariable Integer width, @PathVariable Integer height, HttpServletResponse response) throws Exception {
		imageThumbnail(id, width, height, "attachment", response);
	}

	/**
	 * Image thumbnail.
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param type the type
	 * @param response the response
	 * @throws Exception the exception
	 */
	private void imageThumbnail(Long id, Integer width, Integer height, String type, HttpServletResponse response) throws Exception {
		EpSFile fi = fileService.findById(id);
		if (fi == null) {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
			response.getWriter().print(Result.error("File does't exists.").toJson());
			return;
		}
		if (!readFromCache(fi, width, height, type, response)) {
			FileRepository repo = fileRepositoryFactory.getRepository(fi.getStorageDriver(), fsConfig);
			writeThumbnail(fi, repo.fetchFileAsStream(fi.getStorageId()), width, height, type, response);
		}
	}

	/**
	 * Read from cache.
	 *
	 * @param file the file
	 * @param width the width
	 * @param height the height
	 * @param type the type
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private boolean readFromCache(EpSFile file, int width, int height, String type, HttpServletResponse response) throws Exception {
		EpSFileThumb t = fileService.findThumbByIdAndSize(file.getId(), width, height);
		if (t == null) {
			return false;
		}
		FileRepository repo = fileRepositoryFactory.getRepository(file.getStorageDriver(), fsConfig);
		byte[] data = repo.fetchFileBytes(t.getStorageId());
		response.reset();
		response.addHeader("Content-Disposition", type + ";filename=thumbnail." + t.getFileExt());
		String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(t.getFileExt()), DEFAULT_TYPE);
		response.setContentType(contentType);
		response.addHeader("Content-Length", "" + data.length);
		try {
			response.getOutputStream().write(data);
		} catch (Throwable e) {
			logger.error("Exception occurred when response thumbnail data.\"{}\"", ExceptionUtils.getCause(e));
		}
		return true;
	}

	/**
	 * Write thumbnail.
	 *
	 * @param file the file
	 * @param is the is
	 * @param width the width
	 * @param height the height
	 * @param type the type
	 * @param response the response
	 * @throws Exception the exception
	 */
	private void writeThumbnail(EpSFile file, InputStream is, int width, int height, String type, HttpServletResponse response) throws Exception {
		response.reset();
		response.addHeader("Content-Disposition", type + ";filename=thumbnail." + file.getFileExt());
		String contentType = StringUtils.defaultString(fsConfig.getMimeTypes().get(file.getFileExt()), DEFAULT_TYPE);
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
				String token = defaultFr.storeFile(data);
				EpSFileThumb t = new EpSFileThumb();
				t.setFileExt(file.getFileExt());
				t.setFileId(file.getId());
				t.setFileSize(Long.valueOf(data.length));
				t.setHeight(height);
				t.setWidth(width);
				t.setStorageDriver(defaultFr.getClass().getCanonicalName());
				t.setStorageId(token);
				fileService.saveSysFileThumb(t);
			}
		} finally {
			is.close();
		}
	}
}
