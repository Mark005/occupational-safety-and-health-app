package com.nncompany.rest.controller;

import com.nncompany.api.dto.RequestError;
import com.nncompany.api.dto.userBriefing.UserBriefingCreateDto;
import com.nncompany.api.dto.userBriefing.UserBriefingUpdateDto;
import com.nncompany.api.interfaces.services.UserBriefingService;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.UserBriefingSort;
import com.nncompany.api.model.wrappers.ResponseList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.rest.url}/user-briefing")
public class UserBriefingController {

    @Autowired
    UserBriefingService userBriefingService;

    @ApiOperation(value = "Get user's passed briefings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefings received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target user not found", response = RequestError.class),
    })
    @GetMapping("/conducted-to-me")
    public ResponseEntity<ResponseList> getMyPassedBriefings(@RequestParam Integer page,
                                                             @RequestParam Integer pageSize) {
        Page<UserBriefing> userBriefingsPage = userBriefingService.getUserBriefings(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        userBriefingsPage.getContent(),
                        userBriefingsPage.getTotalElements()));
    }

    @ApiOperation(value = "Get user's briefings by user's id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefings received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target user not found", response = RequestError.class),
    })
    @GetMapping("/conducted-by-user/{userId}")
    public ResponseEntity<Object> getBriefingsByCurrentUser(@PathVariable Integer userId,
                                                            @RequestParam Integer page,
                                                            @RequestParam Integer pageSize) {
        Page<Briefing> briefingsPage = userBriefingService.getBriefingsByCurrentUser(userId, page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        briefingsPage.getContent(),
                        briefingsPage.getTotalElements()));
    }

    @ApiOperation(value = "Get users by briefing id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target briefing not found", response = RequestError.class),
    })
    @GetMapping("/conducted-by-briefing/{briefingId}")
    public ResponseEntity<Object> getUsersByCurrentBriefing(@PathVariable Integer briefingId,
                                                            @RequestParam Integer page,
                                                            @RequestParam Integer pageSize) {
        final Page<User> userPage = userBriefingService.getUsersByCurrentBriefing(briefingId, page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        userPage.getContent(),
                        userPage.getTotalElements()));
    }

    @ApiOperation(value = "Get passed briefing by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Received successfully", response = UserBriefing.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Current conduction not found", response = RequestError.class),
    })
    @GetMapping("/conducted/{id}")
    public ResponseEntity<UserBriefing> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userBriefingService.findById(id));
    }

    @ApiOperation(value = "Get all passed briefings with sorting")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid request params", response = RequestError.class),
    })
    @GetMapping("/conducted")
    public ResponseEntity<Object> getAll(@RequestParam Integer page,
                                         @RequestParam Integer pageSize,
                                         @RequestParam UserBriefingSort sort,
                                         @RequestParam Sort.Direction direction) {
        Page<UserBriefing> briefingPage = userBriefingService.findAll(page, pageSize, sort, direction);
        return ResponseEntity.ok(
                new ResponseList(
                        briefingPage.getContent(),
                        briefingPage.getTotalElements()));
    }


    @ApiOperation(value = "Add new passed briefing")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Briefing conducted successfully", response = UserBriefing.class),
            @ApiResponse(code = 400, message = "Invalid user-briefing json"),
            @ApiResponse(code = 403, message = "Access denied, only admins can conduct briefings", response = RequestError.class),
            @ApiResponse(code = 404, message = "User or briefing not found", response = RequestError.class),
    })
    @PostMapping("/conducted")
    public ResponseEntity<UserBriefing> addConductedBriefing(@RequestBody UserBriefingCreateDto userBriefingDto) {
        return new ResponseEntity<>(userBriefingService.save(userBriefingDto), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update briefing's conduction day")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefing's conduction day updated successfully", response = UserBriefing.class),
            @ApiResponse(code = 400, message = "Invalid user-briefing json or path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Access denied, only admins can conduct briefings", response = RequestError.class),
            @ApiResponse(code = 404, message = "Briefing conduction not found", response = RequestError.class),
    })
    @PatchMapping("/conducted/{id}")
    public ResponseEntity<UserBriefing> updateConductionDate(@PathVariable Integer id,
                                                             @RequestBody UserBriefingUpdateDto userBriefingDto) {
        userBriefingDto.setId(id);
        return new ResponseEntity<>(userBriefingService.update(userBriefingDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete briefing conduction")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Briefing conduction deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid user-briefing json or path variable"),
            @ApiResponse(code = 403, message = "Access denied, only admins can conduct briefings", response = RequestError.class),
            @ApiResponse(code = 404, message = "Briefing conduction not found", response = RequestError.class),
    })
    @DeleteMapping("/conducted/{id}")
    public ResponseEntity<Void> deleteConductedBriefing(@PathVariable Integer id) {
        userBriefingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
