package com.ets.fileupload.auth;

import com.ets.fileupload.model.request.LoginRequest;
import com.ets.fileupload.model.request.UserSignUp;
import com.ets.fileupload.services.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private IUserService userService;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        return ResponseEntity.ok(tokenManager.generateToken(loginRequest.getUserName()));
    }

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUp userSignUpRequest) {
        userService.addUser(userSignUpRequest);
        return ResponseEntity.ok().body(null);
    }


}
