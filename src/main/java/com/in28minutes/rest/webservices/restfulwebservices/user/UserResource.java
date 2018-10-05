package com.in28minutes.rest.webservices.restfulwebservices.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public Resource<User> retrieveUser(@PathVariable int id){
        User user= service.findOne(id);
        if(user==null)
            throw new UserNotFoundException("id-"+id);

        //all-users,server_path+"/users"
        //retrieveAllUsers
        Resource<User> resource=new Resource<User>(user);
        ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).retrieveAllusers());
        resource.add(linkTo.withRel("all-users"));

        //HATEOAS

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> CreateUser(@Valid @RequestBody User user){
        User savedUser=service.save(user);
        //Return status of CREATED
        // create /user/{id}       savedUser.getId()
        URI location=ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public User DeleteUser(@PathVariable int id){
        User user= service.deleteById(id);
        if(user==null)
            throw new UserNotFoundException("id-"+id);
        return user;
    }


}
