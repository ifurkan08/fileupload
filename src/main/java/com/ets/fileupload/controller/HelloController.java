package com.ets.fileupload.controller;

import com.ets.fileupload.model.request.FileInfoRequest;
import com.ets.fileupload.model.response.FileInfoResponse;
import com.ets.fileupload.model.response.UploadFileResponse;
import com.ets.fileupload.services.services.IFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("hello")
public class HelloController {
    @Autowired
    IFileInfoService fileInfoService;

    @GetMapping("furkan")
    public ResponseEntity<FileInfoResponse> getGo() {

        return ResponseEntity.ok(fileInfoService.getAllActiveFileInfo());

    }

    @PostMapping(path = "uploadFile", produces = "application/json",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {

        UploadFileResponse uploadFileResponse = fileInfoService.saveFile(new FileInfoRequest(file, principal.getName()));
        return ResponseEntity.ok(uploadFileResponse);

    }



}
