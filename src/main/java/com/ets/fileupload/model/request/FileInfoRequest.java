package com.ets.fileupload.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoRequest {
    private MultipartFile multipartFile;
    private String addedUser;
}
