package com.ets.fileupload.integration.file;

import com.ets.Session;
import com.ets.fileupload.FileUploadApplicationTests;
import com.ets.fileupload.exceptions.ApiException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder
public class FileControllerTest extends FileUploadApplicationTests {



    @Test
    public void uploadFile_SupportedFileUpload_OK() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.png", "image/png", "some xml".getBytes());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String jsonString = mvcResult.getResponse().getContentAsString();

        Session.fileId = new JSONObject(jsonString).getJSONObject("fileInfoDTO").get("id").toString();
        //.andExpect(status().isOk());
    }

    @Test
    public void uploadFile_NotSupportedFileUpload_BadRequest() throws Exception {
        thrown.expectCause(isA(ApiException.class));
        MockMultipartFile file = new MockMultipartFile("file", "filename.avi", "video/x-msvideo", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    public void getFile_GetExistingFile_OK() throws Exception {
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/file/getFile")
                .param("fileId", Session.fileId)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    @Test
    public void getFile_GetNonexistentFile_BadRequest() throws Exception {
        thrown.expectCause(isA(ApiException.class));
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        int fileId = Integer.parseInt(Session.fileId);
        fileId++;
        mockMvc.perform(MockMvcRequestBuilders
                .get("/file/getFile")
                .param("fileId", String.valueOf(fileId))
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void update_ExistentFile_OK() throws Exception {
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        MockMultipartFile file = new MockMultipartFile("file", "updated.png", "image/png", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .param("id", Session.fileId)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void update_NonExistentFile_BadRequest() throws Exception {
        thrown.expectCause(isA(ApiException.class));
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        MockMultipartFile file = new MockMultipartFile("file", "updated.png", "image/png", "some xml".getBytes());
        int fileId = Integer.parseInt(Session.fileId);
        fileId += 50;
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/updateFile")
                .file(file)
                .param("id", String.valueOf(fileId))
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    public void delete_NonExistentFile_BadRequest() throws Exception {
        thrown.expectCause(isA(ApiException.class));
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        int fileId = Integer.parseInt(Session.fileId);
        fileId += 50;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/file/deleteFile")
                .param("fileId", String.valueOf(fileId))
                .principal(mockPrincipal));
    }

    @Test
    public void delete_ExistedFile_OK() throws Exception {
        if (Session.fileId == null)
            uploadFile_SupportedFileUpload_OK();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/file/deleteFile")
                .param("fileId", Session.fileId)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


}
