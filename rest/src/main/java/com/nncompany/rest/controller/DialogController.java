package com.nncompany.rest.controller;

import com.nncompany.api.dto.message.DialogMessageCreateDto;
import com.nncompany.api.dto.message.MessageTextUpdateDto;
import com.nncompany.api.interfaces.services.MessageService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.Message;
import com.nncompany.api.model.wrappers.ResponseList;
import com.nncompany.rest.annotation.CommonOperation;
import com.nncompany.rest.annotation.OperationCreate;
import com.nncompany.rest.annotation.OperationDelete;
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

    @CommonOperation("Get dialog with user by 'userId' with pagination")
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

    @CommonOperation("Get message by id from dialog with user")
    @GetMapping("/dialog/{messageId}")
    public ResponseEntity<Object> getMessageFromDialogWithUser(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.getDialogMessageById(messageId));
    }

    @OperationCreate("Send message to user")
    @PostMapping("/dialog/{userId}")
    public ResponseEntity<Message> sendDialogMessage(@PathVariable Integer userId,
                                                     @RequestBody DialogMessageCreateDto messageDto) {
        messageDto.setUserToId(userId);
        return new ResponseEntity<>(messageService.saveDialogMessage(messageDto), HttpStatus.CREATED);
    }

    @CommonOperation("Change message's text by message id " +
            "(Attention: user can change only his message and only message's text)")
    @PatchMapping("/dialog/{messageId}")
    public ResponseEntity<Message> changeDialogsMessage(@PathVariable Integer messageId,
                                                        @RequestBody MessageTextUpdateDto messageDto) {
        messageDto.setId(messageId);
        return new ResponseEntity<>(messageService.updateMessageText(messageDto), HttpStatus.OK);
    }

    @OperationDelete("Delete message by message id from dialog with user(Attention: user can delete only his message)")
    @DeleteMapping("/dialog/{messageId}")
    public ResponseEntity<Void> deleteDialogsMessage(@PathVariable Integer messageId) {
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
