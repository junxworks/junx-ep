package io.github.junxworks.ep.fs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;

@Service
public interface FileService {

	void inser(SFile file);

	SFile findById(String id);

	List<SFile> findByGroup(String group);

	List<SFile> findByOrg(String orgNo);

	void deleteById(String id);

	int saveSysFileThumb(SFileThumb t);

	SFileThumb findThumbByIdAndSize(String fileId, int width, int height);

}
