package com.ets.fileupload.services.impl;

import com.ets.fileupload.model.dto.FileInfoDTO;
import com.ets.fileupload.model.entity.FileInfo;
import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;
import com.ets.fileupload.repositories.FileInfoRepository;
import com.ets.fileupload.services.services.IFileInfoService;
import com.ets.fileupload.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class FileInfoServiceImpl implements IFileInfoService {
    @Autowired
    private StorageService storageService;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public UploadFileResponse saveFile(FileInfoRequest fileInfoRequest) {
        FileInfo fileInfo = FileInfo.builder()
                .fileName(FilenameUtils.getBaseName(fileInfoRequest.getMultipartFile().getOriginalFilename()))
                .addedUser(fileInfoRequest.getAddedUser())
                .ext(FilenameUtils.getExtension(fileInfoRequest.getMultipartFile().getOriginalFilename()))
                .size(fileInfoRequest.getMultipartFile().getSize())
                .createDate(new Date())
                .deleted(false)
                .build();
        fileInfoRepository.save(fileInfo);
        storageService.store(fileInfoRequest.getMultipartFile(), fileInfo.getId().toString());
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        return UploadFileResponse.builder().fileInfoDTO
                (fileInfoDTO.convertTo(fileInfo,fileInfoDTO))
                .build();
    }

    @Override
    public FileInfoResponse getAllActiveFileInfo() {
        return new FileInfoResponse(
                fileInfoRepository.findByDeleted(false)
                        .stream().map(p -> FileInfoDTO.builder().build().convertTo(p,new FileInfoDTO())).collect(Collectors.toList()));
    }


}
