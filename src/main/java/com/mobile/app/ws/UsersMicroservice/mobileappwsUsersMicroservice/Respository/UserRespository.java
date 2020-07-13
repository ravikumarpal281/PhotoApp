package com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Respository;

import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRespository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
