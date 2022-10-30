package com.ets.fileupload.model.dto;

import com.ets.fileupload.model.BaseModel;
import com.ets.fileupload.model.entity.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfoDTO extends BaseModel<FileInfoDTO,FileInfo> {
    private Long id;

    private String fileName;

    private String ext;

    private Long size;

    private String addedUser;

    private String updatedUser;

    private Date createDate;

    private Date updateDate;

    @PostConstruct
    public void init() {
        super.setIgnoreProperties(new String[]{"a", "b", "c"});
    }
}
