package com.ets.fileupload.model.dto;

import com.ets.fileupload.model.BaseModel;
import com.ets.fileupload.model.entity.FileInfo;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "FileInfoDTOBuilder")
@Getter
@Setter
public class FileInfoDTO extends BaseModel<FileInfoDTO,FileInfo> {
    private Long id;

    private String fileName;

    private String ext;

    private Long size;

    private String addedUser;

    private String updatedUser;

    private Date createDate;

    private Date updateDate;

}
