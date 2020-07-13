package com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.service;

import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public UserDTO createrUser(UserDTO userDTO);
}
