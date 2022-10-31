package com.ets.fileupload.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoRequest {
    private MultipartFile multipartFile;
    private Long id;
    private String requestingUser;
}
