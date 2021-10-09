package com.nncompany.rest.controller;

import com.nncompany.api.interfaces.services.BriefingService;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.rest.annotation.CommonOperation;
import com.nncompany.rest.annotation.OperationCreate;
import com.nncompany.rest.annotation.OperationDelete;
import com.nncompany.rest.annotation.OperationFindItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.rest.url}")
public class BriefingController {

    @Autowired
    BriefingService briefingService;

    @OperationFindItems("Get briefings with pagination")
    @GetMapping("/briefings")
    @PreAuthorize("hasAnyAuthority('briefings:read')")
    public ResponseEntity<Object> getAllBriefings(@RequestParam Integer page,
                                                  @RequestParam Integer pageSize) {
        Page<Briefing> briefingsPage = briefingService.getWithPagination(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        briefingsPage.getContent(),
                        briefingsPage.getTotalElements()));
    }

    @CommonOperation("Get briefing by id")
    @GetMapping("/briefings/{id}")
    public ResponseEntity<Object> getBriefingById(@PathVariable Integer id) {
        return ResponseEntity.ok(briefingService.findById(id));
    }

    @OperationCreate("Add new briefing (Attention: only admin can add new briefings)")
    @PostMapping("/briefings")
    public ResponseEntity<Object> addBriefing(@RequestBody Briefing briefing) {
        return new ResponseEntity<>(briefingService.save(briefing), HttpStatus.CREATED);
    }

    @CommonOperation("Update briefing by id")
    @PutMapping("/briefings/{id}")
    public ResponseEntity<Object> updateBriefing(@PathVariable Integer id,
                                                 @RequestBody Briefing briefing) {
        briefing.setId(id);
        return new ResponseEntity<>(briefingService.update(briefing), HttpStatus.OK);
    }

    @OperationDelete("Deleter briefing by id")
    @DeleteMapping("/briefings/{id}")
    public ResponseEntity<Object> deleteBriefing(@PathVariable Integer id) {
        briefingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
