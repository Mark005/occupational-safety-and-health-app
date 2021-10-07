package com.nncompany.rest.controller;

import com.nncompany.api.dto.task.TaskChangeStatusDto;
import com.nncompany.api.dto.task.TaskCreateDto;
import com.nncompany.api.interfaces.services.TaskService;
import com.nncompany.api.model.entities.Task;
import com.nncompany.api.model.enums.TaskStatus;
import com.nncompany.api.model.enums.TaskType;
import com.nncompany.api.dto.RequestError;
import com.nncompany.api.model.wrappers.ResponseList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get all available tasks for current user by type and status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid query params", response = RequestError.class),
    })
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

    @ApiOperation(value = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task received successfully", response = Task.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Current task is personal task another user's", response = RequestError.class),
            @ApiResponse(code = 404, message = "Current task not found", response = RequestError.class),
    })
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.findById(id));
    }


    @ApiOperation(value = "Add new task (Attention: only admin can add new tasks)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tasks created successfully", response = Task.class),
            @ApiResponse(code = 400, message = "Invalid task's json, check models for more info", response = RequestError.class),
            @ApiResponse(code = 403, message = "Access denied, only admin can add tasks", response = RequestError.class),
            @ApiResponse(code = 403, message = "Executor not found", response = RequestError.class),
    })
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody TaskCreateDto taskCreateDto){
        return new ResponseEntity<>(taskService.create(taskCreateDto), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Change task's status")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Task's status changed successfully", response = Task.class),
            @ApiResponse(code = 400, message = "Invalid path variable or task json, check models for more info", response = RequestError.class),
            @ApiResponse(code = 404, message = "Current task not found", response = RequestError.class),
    })
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Integer id,
                                           @RequestBody TaskChangeStatusDto taskDto) {
        taskDto.setId(id);
        return new ResponseEntity<>(taskService.changeTaskStatus(taskDto), HttpStatus.OK);
    }


    @ApiOperation(value = "Delete task")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Task deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Access denied, only admin can delete tasks", response = RequestError.class),
            @ApiResponse(code = 404, message = "Current task not found", response = RequestError.class),
    })
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id){
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
