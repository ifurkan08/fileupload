package com.ets.fileupload.services.services;

import com.ets.fileupload.model.request.UserSignUp;
import com.ets.fileupload.model.entity.UserEntity;

import java.util.List;

public interface IUserService {
    UserEntity addUser(UserSignUp userSignUpRequest);
    List<UserEntity> getAllActiveUsers ();
}
