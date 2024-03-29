/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileServiceImpl.java   
 * @Package io.github.junxworks.ep.fs.service.impl   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2021-1-31 16:22:15   
 * @version V1.0 
 * @Copyright: 2021 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.core.orm.SnowFlake;
import io.github.junxworks.ep.fs.entity.EpSFile;
import io.github.junxworks.ep.fs.entity.EpSFileThumb;
import io.github.junxworks.ep.fs.mapper.FileMapper;
import io.github.junxworks.ep.fs.service.FileService;
import io.github.junxworks.junx.core.util.StringUtils;
import jakarta.annotation.PostConstruct;

@Service
public class FileServiceImpl implements FileService, ApplicationContextAware {

	/** 常量 FILE_GROUP_DEFAULT.文件所属分组 */
	private static final String FILE_GROUP_DEFAULT = "default";

	/** 常量 FILE_ORGNO_DEFAULT.文件所属组织机构编码 */
	private static final String FILE_ORGNO_DEFAULT = "default";

	@Autowired
	private FileMapper mapper;

	private SnowFlake snowFlake;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void init() {
		Environment env = applicationContext.getEnvironment();
		Long nodeId = Long.valueOf(env.getProperty("junxep.fs.nodeid", String.valueOf(new Random().nextInt(30))));
		Long centerId = Long.valueOf(env.getProperty("junxep.fs.centerid", String.valueOf(new Random().nextInt(30))));
		snowFlake = new SnowFlake(nodeId, centerId, 1);
	}

	public void inser(EpSFile file) {
		file.setId(snowFlake.nextId());
		if (StringUtils.isNull(file.getFileGroup())) {
			file.setFileGroup(FILE_GROUP_DEFAULT);
		}
		if (StringUtils.isNull(file.getOrgNo())) {
			file.setOrgNo(FILE_ORGNO_DEFAULT);
		}
		mapper.insertWithoutNull(file);
	}

	public EpSFile findById(Long id) {
		return mapper.findById(id);
	}

	public List<EpSFile> findByGroup(String group) {
		return mapper.findByGroup(group);
	}

	public List<EpSFile> findByOrg(String orgNo) {
		return mapper.findByOrg(orgNo);
	}

	public void deleteById(Long id) {
		mapper.deleteById(id);
	}

	public int saveSysFileThumb(EpSFileThumb t) {
		t.setId(snowFlake.nextId());
		t.setCreateTime(new Date());
		return mapper.insertWithoutNull(t);
	}

	public EpSFileThumb findThumbByIdAndSize(Long fileId, int width, int height) {
		List<EpSFileThumb> ts = mapper.queryThumbList(fileId, width, height);
		if (ts.isEmpty()) {
			return null;
		}
		if (ts.size() > 1) {
			for (int i = 1, len = ts.size(); i < len; i++) {
				EpSFileThumb t = ts.get(i);
				mapper.deleteByPK(t);
			}
		}
		return ts.get(0);
	}

}
