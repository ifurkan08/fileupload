package com.ets.fileupload.repositories;

import com.ets.fileupload.model.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    List<FileInfo> findByDeleted(boolean deleted);
}
