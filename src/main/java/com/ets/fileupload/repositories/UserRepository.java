package com.ets.fileupload.repositories;

import com.ets.fileupload.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByActive(String active);

    Optional<UserEntity> findFirstByUserName(String userName);
}
