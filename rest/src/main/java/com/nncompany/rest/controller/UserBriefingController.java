package com.nncompany.rest.controller;

import com.nncompany.api.dto.userBriefing.UserBriefingCreateDto;
import com.nncompany.api.dto.userBriefing.UserBriefingUpdateDto;
import com.nncompany.api.interfaces.services.UserBriefingService;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.entities.User;
import com.nncompany.api.model.entities.UserBriefing;
import com.nncompany.api.model.enums.UserBriefingSort;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.rest.annotation.CommonOperation;
import com.nncompany.rest.annotation.OperationCreate;
import com.nncompany.rest.annotation.OperationDelete;
import com.nncompany.rest.annotation.OperationFindItems;
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

    @OperationFindItems("Get user's passed briefings")
    @GetMapping("/conducted-to-me")
    public ResponseEntity<ResponseList> getMyPassedBriefings(@RequestParam Integer page,
                                                             @RequestParam Integer pageSize) {
        Page<UserBriefing> userBriefingsPage = userBriefingService.getUserBriefings(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        userBriefingsPage.getContent(),
                        userBriefingsPage.getTotalElements()));
    }

    @OperationFindItems("Get user's briefings by user's id")
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

    @OperationFindItems("Get users by briefing id")
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

    @CommonOperation("Get passed briefing by id")
    @GetMapping("/conducted/{id}")
    public ResponseEntity<UserBriefing> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userBriefingService.findById(id));
    }

    @OperationFindItems("Get all passed briefings with sorting")
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

    @OperationCreate("Add new passed briefing")
    @PostMapping("/conducted")
    public ResponseEntity<UserBriefing> addConductedBriefing(@RequestBody UserBriefingCreateDto userBriefingDto) {
        return new ResponseEntity<>(userBriefingService.save(userBriefingDto), HttpStatus.CREATED);
    }

    @CommonOperation("Update briefing's conduction day")
    @PatchMapping("/conducted/{id}")
    public ResponseEntity<UserBriefing> updateConductionDate(@PathVariable Integer id,
                                                             @RequestBody UserBriefingUpdateDto userBriefingDto) {
        userBriefingDto.setId(id);
        return new ResponseEntity<>(userBriefingService.update(userBriefingDto), HttpStatus.OK);
    }

    @OperationDelete("Delete briefing conduction")
    @DeleteMapping("/conducted/{id}")
    public ResponseEntity<Void> deleteConductedBriefing(@PathVariable Integer id) {
        userBriefingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
