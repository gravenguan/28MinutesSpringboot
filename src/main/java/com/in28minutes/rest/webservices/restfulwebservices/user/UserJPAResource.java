package com.in28minutes.rest.webservices.restfulwebservices.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDaoService service;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    //retrieveAllUsers GET /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllusers(){
        return userRepository.findAll();
    }

    //retrieveUser(int id) GET /users/{id}
    @GetMapping("/jpa/users/{id}")
    public Optional<User> retrieveUser(@PathVariable int id){
        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id-"+id);

        //all-users,server_path+"/users"
        //retrieveAllUsers
//        Resource<User> resource=new Resource<User>(user.get());
//        ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).retrieveAllusers());
//        resource.add(linkTo.withRel("all-users"));

        //HATEOAS

        return user;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> CreateUser(@Valid @RequestBody User user){
        User savedUser=userRepository.save(user);
        //Return status of CREATED
        // create /user/{id}       savedUser.getId()
        URI location=ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void DeleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }

        return userOptional.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> CreatePost(@PathVariable int id, @RequestBody Post post){

        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }

        User user=userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location=ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


}