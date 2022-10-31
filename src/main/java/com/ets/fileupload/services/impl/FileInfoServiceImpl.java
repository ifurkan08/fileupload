package com.ets.fileupload.services.impl;

import com.ets.fileupload.model.dto.FileInfoDTO;
import com.ets.fileupload.model.entity.FileInfo;
import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.DeleteFileResponse;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;
import com.ets.fileupload.repositories.FileInfoRepository;
import com.ets.fileupload.services.services.IFileInfoService;
import com.ets.fileupload.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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
                .addedUser(fileInfoRequest.getRequestingUser())
                .ext(FilenameUtils.getExtension(fileInfoRequest.getMultipartFile().getOriginalFilename()))
                .size(fileInfoRequest.getMultipartFile().getSize())
                .createDate(new Date())
                .deleted(false)
                .build();
        String validMessage=fileInfo.validMessage();
        if(!validMessage.equals("OK")){
            return UploadFileResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).errormessage(validMessage).build();
        }
        fileInfoRepository.save(fileInfo);
        storageService
                .store(fileInfoRequest.getMultipartFile(),
                fileInfoRequest.getMultipartFile().getOriginalFilename(),
                fileInfo.getId().toString());
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        return UploadFileResponse.builder().fileInfoDTO
                (fileInfoDTO.convertTo(fileInfo,fileInfoDTO)).httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public FileInfoResponse getAllActiveFileInfo() {
        return new FileInfoResponse(
                fileInfoRepository.findByDeleted(false)
                        .stream().map(p -> FileInfoDTO.builder().build().convertTo(p,new FileInfoDTO())).collect(Collectors.toList()));
    }

    @Override
    public Resource findFileById(Long id) throws EntityNotFoundException {
        FileInfo fileInfo = fileInfoRepository.findById(id).orElse(null);
        if(fileInfo == null){
            throw new EntityNotFoundException("file does not exist");
        }
        Resource resource =storageService.loadAsResource(fileInfo.getFullPath());
        return resource;
    }

    @Override
    public UploadFileResponse updateFile(FileInfoRequest fileInfoRequest) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findById(fileInfoRequest.getId()).orElse(null);
        if(fileInfo == null || fileInfo.isDeleted()){
            return UploadFileResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).errormessage(FileInfo.fileDeletedOrNotExist).build();
        }
        String fullPath= fileInfo.getFullPath();
        fileInfo.setFileName(FilenameUtils.getBaseName(fileInfoRequest.getMultipartFile().getOriginalFilename()));
        fileInfo.setUpdatedUser(fileInfoRequest.getRequestingUser());
        fileInfo.setExt(FilenameUtils.getExtension(fileInfoRequest.getMultipartFile().getOriginalFilename()));
        fileInfo.setSize(fileInfoRequest.getMultipartFile().getSize());
        fileInfo.setUpdateDate(new Date());
        String validMessage=fileInfo.validMessage();
        if(!validMessage.equals("OK")){
            return UploadFileResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).errormessage(validMessage).build();
        }
        fileInfoRepository.save(fileInfo);
        storageService.delete(fullPath);
        storageService
                .store(fileInfoRequest.getMultipartFile(),
                        fileInfoRequest.getMultipartFile().getOriginalFilename(),
                        fileInfo.getId().toString());
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        return UploadFileResponse.builder().fileInfoDTO
                        (fileInfoDTO.convertTo(fileInfo,fileInfoDTO)).httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public DeleteFileResponse deleteFile(Long fileId) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);
        if(fileInfo == null || fileInfo.isDeleted()){
            return DeleteFileResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).errormessage(FileInfo.fileDeletedOrNotExist).build();
        }
        fileInfo.setDeleted(true);
        fileInfoRepository.save(fileInfo);
        storageService.delete(fileInfo.getFullPath());
        return DeleteFileResponse.builder().httpStatus(HttpStatus.OK)
                .build();
    }


}
