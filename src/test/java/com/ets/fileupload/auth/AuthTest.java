package com.ets.fileupload.auth;

import com.ets.Session;
import com.ets.fileupload.FileUploadApplicationTests;
import com.ets.fileupload.model.request.LoginRequest;
import com.ets.fileupload.model.request.UserSignUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder
public class AuthTest extends FileUploadApplicationTests {

    @Test
    public void signUp_Correct_Data_Signup_OK() throws Exception {
        UserSignUp anObject = new UserSignUp();
        anObject.setUserName("furkan3");
        anObject.setPassword("123");
        anObject.setMail("furkan@mail.com");
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void login_Correct_Data_Login_OK() throws Exception {
        LoginRequest anObject = new LoginRequest();
        anObject.setUserName("furkan3");
        anObject.setPassword("123");
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Session.token = mvcResult.getResponse().getContentAsString();
    }
}
