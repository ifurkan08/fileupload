package com.ets.fileupload.services.services;

import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;


public interface IFileInfoService {
    UploadFileResponse saveFile(FileInfoRequest fileInfoRequest);

    FileInfoResponse getAllActiveFileInfo();


}
