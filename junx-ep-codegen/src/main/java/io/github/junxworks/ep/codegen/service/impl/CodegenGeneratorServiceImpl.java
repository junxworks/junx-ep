package io.github.junxworks.ep.codegen.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import io.github.junxworks.ep.auth.model.UserModel;
import io.github.junxworks.ep.codegen.config.EPCodegenConfig;
import io.github.junxworks.ep.codegen.core.CodeGenerator;
import io.github.junxworks.ep.codegen.core.DatabaseElement;
import io.github.junxworks.ep.codegen.core.GenerationContext;
import io.github.junxworks.ep.codegen.core.Table;
import io.github.junxworks.ep.codegen.dto.EpCgGeneratorCondition;
import io.github.junxworks.ep.codegen.dto.EpCgGeneratorDto;
import io.github.junxworks.ep.codegen.dto.FileGenerateCondition;
import io.github.junxworks.ep.codegen.dto.TableListCondition;
import io.github.junxworks.ep.codegen.entity.EpCgGenerator;
import io.github.junxworks.ep.codegen.mapper.CodegenGeneratorMapper;
import io.github.junxworks.ep.codegen.service.CodegenDatasourceService;
import io.github.junxworks.ep.codegen.service.CodegenGeneratorService;
import io.github.junxworks.ep.codegen.service.CodegenTemplateService;
import io.github.junxworks.ep.codegen.vo.EpCgDatasourceVo;
import io.github.junxworks.ep.codegen.vo.EpCgGeneratorVo;
import io.github.junxworks.ep.codegen.vo.EpCgTemplateVo;
import io.github.junxworks.ep.sys.constants.RecordStatus;
import io.github.junxworks.junx.core.exception.BaseRuntimeException;
import io.github.junxworks.junx.core.util.DateUtils;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class CodegenGeneratorServiceImpl implements CodegenGeneratorService {

	private Logger log = LoggerFactory.getLogger(CodegenGeneratorServiceImpl.class);

	@Autowired
	private CodegenGeneratorMapper generatorMapper;

	@Autowired
	private CodegenDatasourceService dsService;

	@Autowired
	private CodegenTemplateService templateService;

	@Autowired
	private EPCodegenConfig config;

	@Override
	public List<EpCgGeneratorVo> queryList(EpCgGeneratorCondition condition) {
		return generatorMapper.queryList(condition);
	}

	@Override
	public EpCgGenerator queryEntityById(Long id) {
		return generatorMapper.selectEntityByID(EpCgGenerator.class, id);
	}

	@Override
	public void saveEntity(EpCgGeneratorDto dto) {
		EpCgGenerator entity = new EpCgGenerator();
		BeanUtils.copyProperties(dto, entity);
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		Long userId = user.getId();
		if (dto.getTemplateList() != null) {
			List<String> tmps = dto.getTemplateList().stream().flatMap(v -> {
				return Stream.of(v.getValue());
			}).collect(Collectors.toList());
			entity.setTemplates(StringUtils.join(tmps.toArray(new String[] {}), ","));
		}
		if (entity.getId() == null) {
			entity.setCreateTime(new Date());
			entity.setCreateUser(userId);
			entity.setStatus(RecordStatus.NORMAL.getValue());
			generatorMapper.insertWithoutNull(entity);
		} else {
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(userId);
			generatorMapper.updateWithoutNull(entity);
		}
	}

	@Override
	public List<Table> queryTableList(TableListCondition condition) {
		String dsId = condition.getDsId();
		if (StringUtils.isNull(dsId)) {
			throw new BaseRuntimeException("数据源ID不能为空");
		}
		EpCgDatasourceVo ds = dsService.queryDatasourceEntityByDsId(dsId);
		if (ds == null) {
			throw new BaseRuntimeException("无效的数据源ID");
		}
		try {
			DatabaseElement db = new DatabaseElement(ds.getDbType(), ds.getConnUrl(), ds.getDbUsername(), ds.getDbPasswd(), ds.getDbName());
			return CodeGenerator.queryTableList(db, condition.getTableName());
		} catch (Exception e) {
			throw new BaseRuntimeException("获取数据库表结构失败", e);
		}
	}

	@Override
	public File generateFiles(FileGenerateCondition condition) throws Exception {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		String dsId = condition.getDsId();
		if (StringUtils.isNull(dsId)) {
			throw new BaseRuntimeException("数据源ID不能为空");
		}
		Long genId = condition.getGenId();
		if (genId == null) {
			throw new BaseRuntimeException("生成器ID不能为空");
		}
		EpCgGenerator gen = generatorMapper.selectEntityByID(EpCgGenerator.class, genId);
		String templates = gen.getTemplates();
		List<String> tmpIds = Lists.newArrayList(templates.split(","));
		if (tmpIds.isEmpty()) {
			throw new BaseRuntimeException("生成器关联模板为空");
		}
		String tables = condition.getTables();
		if (StringUtils.isNull(tables)) {
			throw new BaseRuntimeException("待生成表不能为空");
		}
		String[] ts = tables.split(",");
		EpCgDatasourceVo ds = dsService.queryDatasourceEntityByDsId(dsId);
		if (ds == null) {
			throw new BaseRuntimeException("无效的数据源ID");
		}
		DatabaseElement db = new DatabaseElement(ds.getDbType(), ds.getConnUrl(), ds.getDbUsername(), ds.getDbPasswd(), ds.getDbName());
		GenerationContext context = new GenerationContext();
		context.setDatabase(db);
		String tmpDir = System.getProperty("java.io.tmpdir");
		File tmp = new File(tmpDir);
		File random = new File(tmp, "EP-Codegen-" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + user.getId());
		for (String table : ts) {
			File tableDir = new File(random, table);//table根目录
			if (!tableDir.exists()) {
				tableDir.mkdirs();
			}
			tmpIds.forEach(tmpId -> {
				EpCgTemplateVo t = templateService.queryAvailableTemplateByTmpId(tmpId);
				if (t != null) {
					context.setFileDir(tableDir);
					context.setFileNameExp(t.getOutputName());
					context.setTableName(table);
					context.setTemplate(t.getContent());
					context.setAttr(config.getTemplateAttr());
					try {
						CodeGenerator.generate(context);
					} catch (Exception e) {
						log.error("表[" + table + "]生成代码异常，模板:" + t.getTmpId() + "[" + t.getTmpDesc() + "]", e);
					}
				}
			});
		}
		return random;
	}

}
