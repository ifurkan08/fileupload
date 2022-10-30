package com.ets.fileupload.model.request;

import com.ets.fileupload.model.BaseModel;
import com.ets.fileupload.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUp extends BaseModel<UserSignUp, UserEntity> {
    private String userName;
    private String password;
    private String mail;
}
