package com.example.user_task.rest;

import com.example.user_task.model.User;
import com.example.user_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {



    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity users(){
        return ResponseEntity.ok(userRepository.findAll());
    }



    @PostMapping()
    public ResponseEntity addUser(@RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.ok("created");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserByid(@PathVariable(name = "id") int id){
        try{
            userRepository.delete(id);
            return  ResponseEntity.ok("delete");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("book by "+ id + " does not exist");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity userById(@PathVariable(name = "id") int id ){
        User one = userRepository.findOne(id);
        return ResponseEntity.ok(one);
    }
    @PutMapping()
    public ResponseEntity updateUser(@RequestBody User user){

        if(userRepository.exists(user.getId())){
            userRepository.save(user);
            return ResponseEntity.ok("update");
        }else{
            return ResponseEntity.badRequest().body("User with " + user.getName()+ " does not exist");
        }
    }
}
