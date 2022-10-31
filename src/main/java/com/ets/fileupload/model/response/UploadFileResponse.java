package com.ets.fileupload.model.response;

import com.ets.fileupload.model.dto.FileInfoDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class UploadFileResponse extends ResponseBase{
    private FileInfoDTO fileInfoDTO;
}
