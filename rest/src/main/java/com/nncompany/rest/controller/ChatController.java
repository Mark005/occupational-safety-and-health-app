package com.nncompany.rest.controller;

import com.nncompany.api.dto.message.ChatMessageCreateDto;
import com.nncompany.api.dto.message.MessageTextUpdateDto;
import com.nncompany.api.interfaces.services.MessageService;
import com.nncompany.api.interfaces.services.UserService;
import com.nncompany.api.model.entities.Message;
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
public class ChatController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @OperationFindItems(value = "Get chat message with pagination")
    @GetMapping("/chat")
    public ResponseEntity<Object> getChatsMessages(@RequestParam Integer page,
                                                   @RequestParam Integer pageSize){
        final Page<Message> chatWithPagination = messageService.getChatWithPagination(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        chatWithPagination.getContent(),
                        chatWithPagination.getTotalElements()));
    }

    @CommonOperation("Get chat's message by id")
    @GetMapping("/chat/{msgId}")
    public ResponseEntity<Object> getChatsMessage(@PathVariable Integer msgId){
        return ResponseEntity.ok(messageService.getChatMessageById(msgId));
    }

    @OperationCreate("Send message to common chat")
    @PostMapping("/chat")
    public ResponseEntity<Message> sendMessageToChat(@RequestBody ChatMessageCreateDto messageDto){
        return new ResponseEntity<>(messageService.saveChatMessage(messageDto), HttpStatus.CREATED);
    }

    @CommonOperation("Change chat's message text(Attention: user can change only his message and only message's text)")
    @PatchMapping("/chat/{messageId}")
    public ResponseEntity<Message> changeChatsMessage(@PathVariable Integer messageId,
                                                      @RequestBody MessageTextUpdateDto messageDto) {
        messageDto.setId(messageId);
        return new ResponseEntity<>(messageService.updateMessageText(messageDto), HttpStatus.OK);
    }

    @OperationDelete(value = "Delete chat's message text(Attention: user can delete only his message)")
    @DeleteMapping("/chat/{messageId}")
    public ResponseEntity<Void> deleteChatsMessage(@PathVariable Integer messageId){
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
