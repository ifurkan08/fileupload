package com.ets.fileupload.repositories;

import com.ets.fileupload.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByActive(String active);
}
