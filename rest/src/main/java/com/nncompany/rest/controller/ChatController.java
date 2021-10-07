package com.nncompany.rest.controller;

import com.nncompany.api.dto.message.ChatMessageCreateDto;
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
public class ChatController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @ApiOperation(value = "Get chat message with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chat messages received successfully", response = ResponseList.class),
            @ApiResponse(code = 400, message = "Invalid query params", response = RequestError.class),
    })
    @GetMapping("/chat")
    public ResponseEntity<Object> getChatsMessages(@RequestParam Integer page,
                                                   @RequestParam Integer pageSize){
        final Page<Message> chatWithPagination = messageService.getChatWithPagination(page, pageSize);
        return ResponseEntity.ok(
                new ResponseList(
                        chatWithPagination.getContent(),
                        chatWithPagination.getTotalElements()));
    }

    @ApiOperation(value = "Get chat's message by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chat message received successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Message with current id from private dialog", response = RequestError.class),
            @ApiResponse(code = 404, message = "Message not found", response = RequestError.class)
    })
    @GetMapping("/chat/{msgId}")
    public ResponseEntity<Object> getChatsMessage(@PathVariable Integer msgId){
        return ResponseEntity.ok(messageService.getChatMessageById(msgId));
    }

    @ApiOperation(value = "Send message to common chat")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Message sent successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid message json, for more info check models")
    })
    @PostMapping("/chat")
    public ResponseEntity<Message> sendMessageToChat(@RequestBody ChatMessageCreateDto messageDto){
        return new ResponseEntity<>(messageService.saveChatMessage(messageDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Change chat's message text(Attention: user can change only his message and only message's text)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message changed successfully", response = Message.class),
            @ApiResponse(code = 400, message = "Invalid message json(check models), or path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Message with current id from private dialog, or you are not creator this message", response = RequestError.class),
            @ApiResponse(code = 404, message = "Message not found", response = RequestError.class)
    })
    @PatchMapping("/chat/{messageId}")
    public ResponseEntity<Message> changeChatsMessage(@PathVariable Integer messageId,
                                                      @RequestBody MessageTextUpdateDto messageDto) {
        messageDto.setId(messageId);
        return new ResponseEntity<>(messageService.updateMessageText(messageDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete chat's message text(Attention: user can delete only his message)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Message deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid message json(check models), or path variable", response = RequestError.class),
            @ApiResponse(code = 403, message = "Message with current id from private dialog, or you are not creator this message", response = RequestError.class),
            @ApiResponse(code = 404, message = "Message not found", response = RequestError.class)
    })
    @DeleteMapping("/chat/{messageId}")
    public ResponseEntity<Void> deleteChatsMessage(@PathVariable Integer messageId){
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
