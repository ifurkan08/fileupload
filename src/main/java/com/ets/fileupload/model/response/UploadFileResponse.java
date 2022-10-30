package com.ets.fileupload.model.response;

import com.ets.fileupload.model.dto.FileInfoDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UploadFileResponse extends ResponseBase{
    private FileInfoDTO fileInfoDTO;
}
