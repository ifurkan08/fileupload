package com.ets.fileupload.services.impl;

import com.ets.fileupload.auth.UserDetailsService;
import com.ets.fileupload.model.entity.UserEntity;
import com.ets.fileupload.model.request.UserSignUp;
import com.ets.fileupload.services.services.IUserService;
import com.ets.fileupload.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserEntity addUser(UserSignUp userSignUpRequest) {
        UserEntity user = userSignUpRequest.convertToBase(userSignUpRequest, new UserEntity());
        user=userRepository.save(user);
        userDetailsService.addUser(user);
        return user ;
    }

    @Override
    public List<UserEntity> getAllActiveUsers() {
       return  userRepository.findByActive("E");
    }
}
