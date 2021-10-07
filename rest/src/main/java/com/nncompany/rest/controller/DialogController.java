package com.nncompany.rest.controller;

import com.nncompany.api.dto.message.DialogMessageCreateDto;
import com.nncompany.api.dto.message.MessageTextUpdateDto;
import com.nncompany.api.interfaces.services.MessageService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.Message;
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
public class DialogController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get dialog with user by 'userId' with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dialog received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid path variable or query params", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target user not found", response = RequestError.class),
    })
    @GetMapping("/dialog/{userId}")
    public ResponseEntity<Object> getDialogWithUser(@PathVariable Integer userId,
                                                    @RequestParam Integer page,
                                                    @RequestParam Integer pageSize) {
        Page<Message> dialogWithUserPage = messageService.getDialogWithUser(userId, page, pageSize);

        return ResponseEntity.ok(
                new ResponseList(
                        dialogWithUserPage.getContent(),
                        dialogWithUserPage.getTotalElements()));
    }

    @ApiOperation(value = "Get message by id from dialog with user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dialog received successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Current message from another dialog", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target user or message not found", response = RequestError.class),
    })
    @GetMapping("/dialog/{messageId}")
    public ResponseEntity<Object> getMessageFromDialogWithUser(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.getDialogMessageById(messageId));
    }

    @ApiOperation(value = "Send message to user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Message sent successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target user not found", response = RequestError.class),
    })
    @PostMapping("/dialog/{userId}")
    public ResponseEntity<Message> sendDialogMessage(@PathVariable Integer userId,
                                                     @RequestBody DialogMessageCreateDto messageDto) {
        messageDto.setUserToId(userId);
        return new ResponseEntity<>(messageService.saveDialogMessage(messageDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Change message's text by message id from dialog with user(Attention: user can change only his message and only message's text)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message text changed successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Current message from another dialog, or you are not creator", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target message not found", response = RequestError.class),
    })
    @PatchMapping("/dialog/{messageId}")
    public ResponseEntity<Message> changeDialogsMessage(@PathVariable Integer messageId,
                                                        @RequestBody MessageTextUpdateDto messageDto) {
        messageDto.setId(messageId);
        return new ResponseEntity<>(messageService.updateMessageText(messageDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete message by message id from dialog with user(Attention: user can delete only his message)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Message deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Current message from another dialog", response = RequestError.class),
            @ApiResponse(code = 404, message = "Target message not found", response = RequestError.class),
    })
    @DeleteMapping("/dialog/{messageId}")
    public ResponseEntity<Void> deleteDialogsMessage(@PathVariable Integer messageId) {
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
