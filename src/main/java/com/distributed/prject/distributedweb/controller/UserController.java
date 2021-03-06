package com.distributed.prject.distributedweb.controller;

import com.distributed.prject.distributedweb.security.UserAuthorizationService;
import io.swagger.annotations.Api;
import com.distributed.prject.distributedweb.model.User;
import io.swagger.annotations.ApiParam;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.distributed.prject.distributedweb.repository.UserRepository;
import com.distributed.prject.distributedweb.requests.LoginRequest;
import com.distributed.prject.distributedweb.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Api(description = "user com.distributed.prject.distributedweb.controller for mobile app")
@RequestMapping(value = "/api/v1/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository u;
    @Autowired
    UserAuthorizationService userAuthorizationService;
    @ApiMethod(description = "Sign up API ")
    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> signUp(@RequestBody @Valid User user   ) {

        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(sha256hex);
        return userService.signup(user);
    }


    @ApiMethod(description = "Sign up API ")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> signIn(@RequestBody @Valid LoginRequest loginRequest   ) {

        return userService.signIn(loginRequest);
    }


    @ApiMethod(description = "update use details ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user ,  @ApiPathParam(name = "id", description = "user ID") @PathVariable("id") int id) {

        return userService.updateUser(user,id);
    }

    @ApiMethod(description = "delete user")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleterUser(  @ApiPathParam(name = "id", description = "user ID") @PathVariable("id") int id) {

        return userService.deleteUser(id);
    }

    @ApiMethod(description = "return all users")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getAllusers() {

        return u.findAll();
    }

}
