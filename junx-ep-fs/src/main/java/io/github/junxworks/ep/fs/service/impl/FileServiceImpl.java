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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;
import io.github.junxworks.ep.fs.mapper.FileMapper;
import io.github.junxworks.ep.fs.service.FileService;
import io.github.junxworks.junx.core.util.StringUtils;

@Service
public class FileServiceImpl implements FileService {

	/** 常量 FILE_GROUP_DEFAULT.文件所属分组 */
	private static final String FILE_GROUP_DEFAULT = "default";

	/** 常量 FILE_ORGNO_DEFAULT.文件所属组织机构编码 */
	private static final String FILE_ORGNO_DEFAULT = "default";

	@Autowired
	private FileMapper mapper;

	public void inser(SFile file) {
		file.setId(UUID.randomUUID().toString());
		if (StringUtils.isNull(file.getFileGroup())) {
			file.setFileGroup(FILE_GROUP_DEFAULT);
		}
		if (StringUtils.isNull(file.getOrgNo())) {
			file.setOrgNo(FILE_ORGNO_DEFAULT);
		}
		mapper.insertWithoutNull(file);
	}

	public SFile findById(String id) {
		return mapper.findById(id);
	}

	public List<SFile> findByGroup(String group) {
		return mapper.findByGroup(group);
	}

	public List<SFile> findByOrg(String orgNo) {
		return mapper.findByOrg(orgNo);
	}

	public void deleteById(String id) {
		mapper.deleteById(id);
	}

	public int saveSysFileThumb(SFileThumb t) {
		t.setId(UUID.randomUUID().toString());
		t.setCreateTime(new Date());
		return mapper.insertWithoutNull(t);
	}

	public SFileThumb findThumbByIdAndSize(String fileId, int width, int height) {
		List<SFileThumb> ts = mapper.queryThumbList(fileId, width, height);
		if (ts.isEmpty()) {
			return null;
		}
		if (ts.size() > 1) {
			for (int i = 1, len = ts.size(); i < len; i++) {
				SFileThumb t = ts.get(i);
				mapper.deleteByPK(t);
			}
		}
		return ts.get(0);
	}

}
