package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {
    private static List<User> users=new ArrayList<>();
    private static Integer usersCount=4;

    static{
        users.add(new User(1,"Adam",new Date()));
        users.add(new User(2,"Eve",new Date()));
        users.add(new User(3,"Jack",new Date()));
        users.add(new User(4,"Heran",new Date()));
    }

    //public List<User> findAll(){}

    public List<User> findAll(){
        return users;
    }

    //public User save(User user)(){}

    public User save(User user){
        if(user.getId()==null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }
    //public User findOne(int id){}
    public User findOne(int id){
        for(User user:users){
            if(user.getId()==id){
                return user;
            }
        }

        return null;
    }


    public User deleteById(int id){
        Iterator<User> it=users.iterator();

        while(it.hasNext()){
            User user = it.next();
            if(user.getId()==id){
                it.remove();
                return user;
            }
        }

        return null;
    }


}
