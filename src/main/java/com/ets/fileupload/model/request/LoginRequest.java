package com.ets.fileupload.model.request;

import com.ets.fileupload.model.BaseModel;
import com.ets.fileupload.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends BaseModel<LoginRequest, UserEntity> {
    private String userName;
    private String password;
}
