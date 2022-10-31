package com.ets.fileupload.controller;

import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.DeleteFileResponse;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;
import com.ets.fileupload.services.services.IFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    IFileInfoService fileInfoService;

    @GetMapping("getAllActiveFiles")
    public ResponseEntity<FileInfoResponse> getAllActiveFiles() {

        return ResponseEntity.ok(fileInfoService.getAllActiveFileInfo());

    }

    @PostMapping(path = "uploadFile", produces = "application/json",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        UploadFileResponse uploadFileResponse = fileInfoService.saveFile(FileInfoRequest.builder().multipartFile(file)
                .requestingUser(principal.getName()).build());
        return ResponseEntity.status(uploadFileResponse.getHttpStatus()).body(uploadFileResponse);
    }
    @GetMapping("getFile")
    public ResponseEntity<?> getFile(@RequestParam("fileId") Long id) {
        try {
            Resource resource = null;
            resource = fileInfoService.findFileById(id);
            if (resource == null) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
        catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "updateFile", produces = "application/json",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadFileResponse> updateFile(@RequestParam("file") MultipartFile file,@RequestParam("id") Long id ,Principal principal) throws IOException {
        UploadFileResponse updateFileResponse = fileInfoService.updateFile(FileInfoRequest.builder().multipartFile(file)
                .requestingUser(principal.getName())
                .id(id).build());
        return ResponseEntity.status(updateFileResponse.getHttpStatus()).body(updateFileResponse);
    }

    @DeleteMapping(path = "deleteFile")
    public ResponseEntity<DeleteFileResponse> deleteFile(@RequestParam("fileId") Long id) throws IOException {
        DeleteFileResponse deleteFileResponse = fileInfoService.deleteFile(id);
        return ResponseEntity.status(deleteFileResponse.getHttpStatus()).body(deleteFileResponse);
    }


}
