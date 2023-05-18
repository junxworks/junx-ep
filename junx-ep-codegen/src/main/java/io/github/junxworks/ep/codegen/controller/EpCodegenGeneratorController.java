package io.github.junxworks.ep.codegen.controller;

import java.io.File;
import java.net.URLEncoder;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.github.junxworks.ep.codegen.dto.EpCgGeneratorCondition;
import io.github.junxworks.ep.codegen.dto.EpCgGeneratorDto;
import io.github.junxworks.ep.codegen.dto.FileGenerateCondition;
import io.github.junxworks.ep.codegen.dto.TableListCondition;
import io.github.junxworks.ep.codegen.service.CodegenGeneratorService;
import io.github.junxworks.ep.core.Result;
import io.github.junxworks.ep.core.utils.PageUtils;
import io.github.junxworks.ep.core.utils.ZipUtils;
import io.github.junxworks.ep.sys.annotations.EpLog;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 生成器管理
 */
@RestController
@RequestMapping("/ep/codegen/generators")
public class EpCodegenGeneratorController {
	private Logger log = LoggerFactory.getLogger(EpCodegenGeneratorController.class);

	@Autowired
	private CodegenGeneratorService generatorService;

	/**
	 * Query list.
	 *
	 * @param condition the condition
	 * @return the result
	 */
	@GetMapping()
	public Result queryList(EpCgGeneratorCondition condition) {
		//查询列表数据
		PageUtils.setPage(condition);
		return Result.ok(new PageInfo<>(generatorService.queryList(condition)));
	}

	@GetMapping("/tables")
	public Result queryTableList(TableListCondition condition) {
		return Result.ok(generatorService.queryTableList(condition));
	}

	@GetMapping("/files")
	public void generateFiles(FileGenerateCondition condition, HttpServletResponse response) throws Exception {
		File dir = generatorService.generateFiles(condition);
		try {
			if (dir != null) {
				ServletOutputStream out = response.getOutputStream();
				response.setHeader("content-disposition", "attachment;fileName="+URLEncoder.encode(dir.getName()+".zip", "UTF-8"));
				ZipUtils.zipDir(dir, out, true);
			} else {
				throw new BaseRuntimeException("没有文件生成");
			}
		} catch (Exception e) {
			log.error("生成代码异常", e);
			throw e;
		} finally {
			FileUtils.deleteDirectory(dir);
		}
	}

	/**
	 * Save .
	 *
	 * @param dto the dto
	 * @return the result
	 */
	@EpLog("EP-代码生成-保存生成器")
	@PostMapping()
	public Result saveEntity(@RequestBody EpCgGeneratorDto dto) {
		generatorService.saveEntity(dto);
		return Result.ok();
	}

	/**
	 * Query entity by id.
	 *
	 * @param id the id
	 * @return the result
	 */
	@GetMapping("/{id}")
	public Result queryDatasourceEntityById(@PathVariable("id") Long id) {
		return Result.ok(generatorService.queryEntityById(id));
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the result
	 */
	@EpLog("EP-代码生成-删除生成器")
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable("id") Long id) {
		EpCgGeneratorDto dto = new EpCgGeneratorDto();
		dto.setId(id);
		dto.setStatus(RecordStatus.DELETED.getValue());
		generatorService.saveEntity(dto);
		return Result.ok();
	}
}
