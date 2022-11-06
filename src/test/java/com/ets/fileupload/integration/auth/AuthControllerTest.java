package com.ets.fileupload.integration.auth;


import com.ets.Session;
import com.ets.fileupload.FileUploadApplicationTests;
import com.ets.fileupload.model.request.LoginRequest;
import com.ets.fileupload.model.request.UserSignUp;
import com.ets.fileupload.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder
public class AuthControllerTest extends FileUploadApplicationTests {
    UserRepository userRepository;

    @Test
    public void signUp_CorrectData_OK() throws Exception {

        int length = 5;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        generatedString += new Date().getTime();
        UserSignUp anObject = new UserSignUp();
        anObject.setUserName(generatedString);
        Session.userName = generatedString;
        anObject.setPassword(Session.password);
        anObject.setMail("i.furkanwwe@gmail.com");
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(anObject);
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void login_CorrectData_OK() throws Exception {
        if(Session.userName == null){
            signUp_CorrectData_OK();
        }
        LoginRequest anObject = new LoginRequest();
        anObject.setUserName(Session.userName);
        anObject.setPassword(Session.password);
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(anObject);
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Session.token = mvcResult.getResponse().getContentAsString();

    }

    @Test
    public void signUp_WithExistingUserName_BadRequest() throws Exception {
        thrown.expectCause(isA(IllegalArgumentException.class));
        if(Session.userName == null){
            signUp_CorrectData_OK();
        }
        UserSignUp anObject = new UserSignUp();
        anObject.setUserName(Session.userName);
        anObject.setPassword(Session.password);
        anObject.setMail("i.furkanwwe@gmail.com");
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(anObject);
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void login_WithNotExistingUserName_BadRequest() throws Exception {
        thrown.expectCause(isA(BadCredentialsException.class));
        int length = 5;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        generatedString += new Date().getTime();
        if(Session.userName == null){
            signUp_CorrectData_OK();
        }
        LoginRequest anObject = new LoginRequest();
        anObject.setUserName(generatedString);
        anObject.setPassword("123");
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(anObject);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @AfterAll
    public void deleteData(){
        if(Session.userName != null){
            userRepository.findFirstByUserName(Session.userName).ifPresent(userEntity -> userRepository.delete(userEntity));
        }
    }

}
