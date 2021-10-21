package com.nncompany.rest.controller;

import com.nncompany.api.dto.task.TaskChangeStatusDto;
import com.nncompany.api.dto.task.TaskCreateDto;
import com.nncompany.api.interfaces.services.TaskService;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.rest.annotation.CommonOperation;
import com.nncompany.rest.annotation.OperationCreate;
import com.nncompany.rest.annotation.OperationDelete;
import com.nncompany.rest.annotation.OperationFindItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.rest.url}")
public class TaskController {

    @Autowired
    TaskService taskService;

    @OperationFindItems(value = "Get all available tasks for current user by type and status")
    @GetMapping("/tasks")
    public ResponseEntity<Object> getAllTasks(@RequestParam Integer page,
                                              @RequestParam Integer pageSize,
                                              @RequestParam TaskType type,
                                              @RequestParam TaskStatus status){
        Page<Task> tasksPage = taskService.findAllAvailableTasksForCurrentUser(type, status, page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        tasksPage.getContent(),
                        tasksPage.getTotalElements()));
    }

    @CommonOperation(value = "Get task by id")
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.findById(id));
    }


    @OperationCreate(value = "Add new task (Attention: only admin can add new tasks)")
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody TaskCreateDto taskCreateDto){
        return new ResponseEntity<>(taskService.create(taskCreateDto), HttpStatus.CREATED);
    }


    @CommonOperation(value = "Change task's status")
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Integer id,
                                           @RequestBody TaskChangeStatusDto taskDto) {
        taskDto.setId(id);
        return new ResponseEntity<>(taskService.changeTaskStatus(taskDto), HttpStatus.OK);
    }


    @OperationDelete(value = "Delete task")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id){
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
