package io.github.junxworks.ep.fs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.junxworks.ep.fs.entity.EpSFile;
import io.github.junxworks.ep.fs.entity.EpSFileThumb;

@Service
public interface FileService {

	void inser(EpSFile file);

	EpSFile findById(Long id);

	List<EpSFile> findByGroup(String group);

	List<EpSFile> findByOrg(String orgNo);

	void deleteById(Long id);

	int saveSysFileThumb(EpSFileThumb t);

	EpSFileThumb findThumbByIdAndSize(Long fileId, int width, int height);

}
