package io.github.junxworks.ep.fs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.junxworks.ep.fs.entity.SFile;
import io.github.junxworks.ep.fs.entity.SFileThumb;
import io.github.junxworks.ep.fs.mapper.FSMapper;
import io.github.junxworks.junx.core.executor.StandardThreadExecutor;

@Service
public class FileService {

	@Autowired
	private FSMapper mapper;

	@Autowired
	private StandardThreadExecutor executor;

	public void inser(SFile file) {
		mapper.insert(file);
	}

	public SFile findById(Object id) {
		return mapper.findById(Long.valueOf(id.toString()));
	}

	public List<SFile> findByGroup(String group) {
		return mapper.findByGroup(group);
	}

	public List<SFile> findByOrg(String orgNo) {
		return mapper.findByOrg(orgNo);
	}

	public void deleteById(Object id) {
		mapper.deleteById(Long.valueOf(String.valueOf(id)));
	}

	public int saveSysFileThumb(SFileThumb t) {
		return mapper.insertWithoutNull(t);
	}

	public SFileThumb findThumbByIdAndSize(Long fileId, int width, int height) {
		List<SFileThumb> ts = mapper.queryThumbList(fileId, width, height);
		if (ts.isEmpty()) {
			return null;
		}
		if (ts.size() > 1) {
			for (int i = 1, len = ts.size(); i < len; i++) {
				final SFileThumb t = ts.get(i);
				executor.submit(new Runnable() {
					@Override
					public void run() {
						mapper.deleteByPK(t);
					}

				});
			}
		}
		return ts.get(0);
	}

}
