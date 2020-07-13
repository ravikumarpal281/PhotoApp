package com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.Controllers;

import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.DTO.UserDTO;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.UI.RequestModel.UserRequestModel;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.UI.ResponseModel.UserResponseModel;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    IUserService userService;

    @GetMapping("/status/check")
    public String status(){
        logger.debug("Inside status check   ");
        return "Active";
    }

    @PostMapping
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userRequestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = mapper.map(userRequestModel, UserDTO.class);
        logger.debug("Before calling service");
        UserDTO returnDTO = userService.createrUser(userDTO);
        ModelMapper dtoMapper = new ModelMapper();
        dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserResponseModel returnUserResponse = dtoMapper.map(returnDTO, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnUserResponse);
    }
}
