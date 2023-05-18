package io.github.junxworks.ep.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.fs.FsClient;
import io.github.junxworks.ep.core.fs.FsMode;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ep/files")
@ConditionalOnBean(FsClient.class)
public class EpFileController {
	@Autowired
	private FsClient fsClient;

	/**
	 * 查看文件元数据
	 *
	 * @param id the id
	 * @return the result
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/metadata")
	public Result fileMeta(@PathVariable("id") Long id) throws Exception {
		return Result.ok(fsClient.getFileInfo(id));
	}

	/**
	 * 下载文件附件.会将文件作为附件的方式下载到客户端本地
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/attachments")
	public void attachment(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
		fsClient.downloadAttachmentIntoResponse(id, response);
	}

	/**
	 * 将文件以图片原文件的方式，直接在客户端浏览器打开
	 *
	 * @param id the id
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/images")
	public void showOriginalFile(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
		fsClient.downloadIntoResponse(id, response);
	}

	@GetMapping("/{idExt}")
	public void showFile(@PathVariable("idExt") String idExt, HttpServletResponse response) throws Exception {
		Long id = null;
		if (idExt.contains(".")) {
			id = Long.valueOf(idExt.split("\\.")[0]);
		} else {
			id = Long.valueOf(idExt);
		}
		fsClient.downloadIntoResponse(id, response);
	}

	/**
	 * 查看文件缩略图.会直接在客户浏览器打开缩略图
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/images/thumbnails")
	public void thumbnail(@PathVariable("id") Long id, @RequestParam(name = "width", required = true) int width, @RequestParam(name = "height", required = true) int height, HttpServletResponse response) throws Exception {
		fsClient.downloadThumbnailIntoResponse(id, width, height, response);
	}

	/**
	 * 将缩略图以附件的方式，下载到客户端本地
	 *
	 * @param id the id
	 * @param width the width
	 * @param height the height
	 * @param response the response
	 * @throws Exception the exception
	 */
	@GetMapping("/{id}/images/thumbnails/attachments")
	public void thumbnailAttachment(@PathVariable("id") Long id, @RequestParam(name = "width", required = true) int width, @RequestParam(name = "height", required = true) int height, HttpServletResponse response) throws Exception {
		fsClient.downloadThumbnailAttachmentIntoResponse(id, width, height, response);
	}

	/**
	 * 单文件上传
	 *
	 * @param file the file
	 * @return the result
	 * @throws Exception the exception
	 */
	@PostMapping()
	public Result uploadSingle(MultipartFile file) throws Exception {
		return fsClient.upload(file);
	}

	@PostMapping("/oss")
	public Result uploadSingleOss(MultipartFile file) throws Exception {
		return fsClient.upload(FsMode.OSS.getMode(), file);
	}

	@PostMapping("/local")
	public Result uploadSingleLocal(MultipartFile file) throws Exception {
		return fsClient.upload(FsMode.LOCAL_FS.getMode(), file);
	}
}
