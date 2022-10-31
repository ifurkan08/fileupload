package com.ets.fileupload.services.services;

import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.DeleteFileResponse;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;
import org.springframework.core.io.Resource;
import java.io.IOException;


public interface IFileInfoService {
    UploadFileResponse saveFile(FileInfoRequest fileInfoRequest);

    FileInfoResponse getAllActiveFileInfo();

    Resource findFileById(Long id) throws IOException;

    UploadFileResponse updateFile(FileInfoRequest fileInfoRequest) throws IOException;

    DeleteFileResponse deleteFile(Long fileId) throws IOException;
}
