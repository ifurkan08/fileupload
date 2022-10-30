package com.ets.fileupload.model.response;

import com.ets.fileupload.model.dto.FileInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoResponse extends ResponseBase {
    List<FileInfoDTO> fileInfoList;
}
