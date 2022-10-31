package com.ets.fileupload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file, String fileName, String id);

	Stream<Path> loadAll();

	Path load(String fullPath);

	Resource loadAsResource(String fullPath);

	void deleteAll();

	void delete(String fullPath) throws IOException;

}
