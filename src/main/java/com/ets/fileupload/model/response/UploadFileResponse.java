package com.ets.fileupload.model.response;

import com.ets.fileupload.model.dto.FileInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse{
    private FileInfoDTO fileInfoDTO;
}
