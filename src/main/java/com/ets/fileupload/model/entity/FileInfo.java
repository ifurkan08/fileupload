package com.ets.fileupload.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "fileinfo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileInfo implements Serializable {

    public static final String BACK_SLASH = "\\";

    public static final String DOT = ".";

    private static final Set<String> extensionList = Set.of("png", "jpeg", "jpg", "docx", "pdf", "xlsx");

    public static final String unsupportedErrorMessage="Unsupported extension";

    public static final String fileSizeErrorMessage="File size too large";

    public static final String fileDeletedOrNotExist="The file trying to update does not exist or has been deleted";


    private static Long maxFileSize = 5242880L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String ext;

    private Long size;

    private String addedUser;

    private String updatedUser;

    private Date createDate;

    private Date updateDate;

    private boolean deleted;

    public String getFullPath() {
        return id + BACK_SLASH + fileName + DOT + ext;
    }

    public String validMessage(){
        if(!extensionList.contains(ext)){
            return unsupportedErrorMessage;
        }
        if(maxFileSize.compareTo(size) < 0){
            return fileSizeErrorMessage;
        }
        return "OK";
    }
}
