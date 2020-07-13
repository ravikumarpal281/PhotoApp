package com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.service;

import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Controllers.UserController;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.DTO.UserDTO;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Entity.UserEntity;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Respository.UserRespository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRespository userRespository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRespository userRespository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRespository = userRespository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createrUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        logger.debug("Before calling Repository");
        userRespository.save(userEntity);
        ModelMapper returnDTO = new ModelMapper();
        returnDTO.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return returnDTO.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.debug("Before calling repo for find by email");
        UserEntity userEntity = userRespository.findByEmail(s);
        if(userEntity == null) throw new UsernameNotFoundException(s);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());

    }
}