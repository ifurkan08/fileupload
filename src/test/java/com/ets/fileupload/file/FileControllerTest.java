package com.ets.fileupload.file;

import com.ets.Session;
import com.ets.fileupload.auth.AuthTest;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.security.Principal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder
public class FileControllerTest extends AuthTest {


    @Test
    public void uploadFile_Upload_OK() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        MockMultipartFile file = new MockMultipartFile("file", "filename.png", "image/png", "some xml".getBytes());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String jsonString= mvcResult.getResponse().getContentAsString();

        Session.fileId =  new JSONObject(jsonString).getJSONObject("fileInfoDTO").get("id").toString();
        //.andExpect(status().isOk());
    }
    @Test
    public void uploadFile_NotSupported_File_Upload_BadRequest() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        MockMultipartFile file = new MockMultipartFile("file", "filename.avi", "video/x-msvideo", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
    @Test
    public void getFile_Get_Existing_File_OK() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/file/getFile")
                .param("fileId",Session.fileId)
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }



    @Test
    public void getFile_Get_Nonexistent_File_BadRequest() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        Integer fileId= Integer.parseInt(Session.fileId);
        fileId++;
        mockMvc.perform(MockMvcRequestBuilders
                .get("/file/getFile")
                .param("fileId",fileId.toString())
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void update_existent_File_OK() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        MockMultipartFile file = new MockMultipartFile("file", "updated.png", "image/png", "some xml".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/uploadFile")
                .file(file)
                .param("id",Session.fileId)
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void update_NonExistent_File_BadRequest() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        MockMultipartFile file = new MockMultipartFile("file", "updated.png", "image/png", "some xml".getBytes());
        Integer fileId = Integer.parseInt(Session.fileId);
        fileId+=50;
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/file/updateFile")
                .file(file)
                .param("id",fileId.toString())
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void delete_nonExistent_File_BadRequest() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        Integer fileId = Integer.parseInt(Session.fileId);
        fileId += 50;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/file/deleteFile")
                .param("fileId",fileId.toString())
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void delete_existent_File_OK() throws Exception {
        if(Session.token == null)
            login_Correct_Data_Login_OK();
        if(Session.fileId == null)
            uploadFile_Upload_OK();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/file/deleteFile")
                .param("fileId",Session.fileId)
                .principal(mockPrincipal).header("authorization", "Bearer " + Session.token)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }


}
