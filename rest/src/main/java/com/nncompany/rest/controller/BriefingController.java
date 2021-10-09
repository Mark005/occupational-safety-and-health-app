package com.nncompany.rest.controller;

import com.nncompany.api.interfaces.services.BriefingService;
import com.nncompany.api.model.entities.Briefing;
import com.nncompany.api.dto.RequestError;
import com.nncompany.api.model.wrappers.ResponseList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get briefings with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefings received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid query params", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find briefings", response = RequestError.class)
    })
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

    @ApiOperation(value = "Get briefing by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefing received successfully", response = Briefing.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Can't find briefing", response = RequestError.class)
    })
    @GetMapping("/briefings/{id}")
    public ResponseEntity<Object> getBriefingById(@PathVariable Integer id) {
        return ResponseEntity.ok(briefingService.findById(id));
    }

    @ApiOperation(value = "Add new briefing (Attention: only admin can add new briefings)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Briefing created successfully", response = Briefing.class),
            @ApiResponse(code = 400, message = "Invalid request body, check models for more info"),
            @ApiResponse(code = 403, message = "Hasn't access, relogin as admin", response = RequestError.class)
    })
    @PostMapping("/briefings")
    public ResponseEntity<Object> addBriefing(@RequestBody Briefing briefing) {
        return new ResponseEntity<>(briefingService.save(briefing), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update briefing by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Briefing updated successfully", response = Briefing.class),
            @ApiResponse(code = 400, message = "Invalid request body or URL path variable, check models for more info"),
            @ApiResponse(code = 403, message = "Hasn't access, relogin as admin", response = RequestError.class),
            @ApiResponse(code = 404, message = "Briefing with current id is not found", response = RequestError.class)
    })
    @PutMapping("/briefings/{id}")
    public ResponseEntity<Object> updateBriefing(@PathVariable Integer id,
                                         @RequestBody Briefing briefing) {
        briefing.setId(id);
        return new ResponseEntity<>(briefingService.update(briefing), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleter briefing by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Briefing deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid URL path variable"),
            @ApiResponse(code = 403, message = "Hasn't access, relogin as admin", response = RequestError.class),
            @ApiResponse(code = 404, message = "Briefing with current id is not found", response = RequestError.class)
    })
    @DeleteMapping("/briefings/{id}")
    public ResponseEntity<Object> deleteBriefing(@PathVariable Integer id) {
        briefingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
