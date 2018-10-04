package com.in28minutes.rest.webservices.restfulwebservices.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    //retrieveAllUsers GET /users
    @GetMapping("/users")
    public List<User> retrieveAllusers(){
        return service.findAll();
    }

    //retrieveUser(int id) GET /users/{id}
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        return service.findOne(id);
    }


}
