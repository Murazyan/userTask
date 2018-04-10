package com.example.user_task.rest;

import com.example.user_task.model.Task;
import com.example.user_task.model.User;
import com.example.user_task.repository.TaskRepository;
import com.example.user_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity tasks(){
        return ResponseEntity.ok(taskRepository.findAll());
    }


    @PostMapping()
    public ResponseEntity addTask(@RequestBody Task task){
        taskRepository.save(task);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskByid(@PathVariable(name = "id") int id){
        try{
            taskRepository.delete(id);
            return  ResponseEntity.ok("delete");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("task by "+ id + " does not exist");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity taskById(@PathVariable(name = "id") int id ){
        Task one = taskRepository.findOne(id);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/byUserId/{id}")
    public ResponseEntity getTaskByUserId(@PathVariable(name = "id") int id){
        List<Task> byUserId = taskRepository.findAllByUserId(id);
        if (byUserId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with " + id + " id no found");
        }
        return ResponseEntity.ok(byUserId);
    }

}
