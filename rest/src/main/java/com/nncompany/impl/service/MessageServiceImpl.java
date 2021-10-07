package com.nncompany.impl.service;

import com.nncompany.api.dto.message.ChatMessageCreateDto;
import com.nncompany.api.dto.message.DialogMessageCreateDto;
import com.nncompany.api.dto.message.MessageTextUpdateDto;
import com.nncompany.api.interfaces.services.MessageService;
import com.nncompany.api.interfaces.store.MessageStore;
import com.nncompany.api.interfaces.store.UserStore;
import com.nncompany.api.model.entities.Message;
import com.nncompany.api.model.entities.User;
import com.nncompany.impl.exception.EntityNotFoundException;
import com.nncompany.impl.exception.NotChatMessageException;
import com.nncompany.impl.exception.NotDialogMessageException;
import com.nncompany.impl.exception.PermissionDeniedException;
import com.nncompany.impl.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageStore messageStore;

    @Autowired
    UserStore userStore;

    @Override
    public Message getChatMessageById(Integer id) {
        final Message message = messageStore.getById(id);
        if (message.getUserTo() != null) {
            throw new NotChatMessageException("It isn't chat message");
        }
        return message;
    }

    public Message getDialogMessageById(Integer messageId) {
        Message message = messageStore
                .findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message with current id not found"));

        User currentUser = SecurityUtils.getCurrentUser();
        if (message.getUserTo() == null) {
            throw new NotDialogMessageException("It isn't dialog message");
        }

        if (!Objects.equals(currentUser, message.getUserFrom()) ||
                !Objects.equals(currentUser, message.getUserTo())) {
            throw new PermissionDeniedException("You are not creator or recipient");
        }

        return message;
    }

    @Override
    public Page<Message> getDialogWithUser(Integer userId, Integer page, Integer pageSize) {
        User userTwo = userStore
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with current id not found"));

        return messageStore.findAllMessagesFromDialog(
                SecurityUtils.getCurrentUser(),
                userTwo,
                PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Message> getChatWithPagination(Integer page, Integer pageSize) {
        return messageStore.findAllByUserToIsNull(PageRequest.of(page, pageSize));
    }

    @Override
    public Message saveChatMessage(ChatMessageCreateDto messageDto) {
        Message message = Message.builder()
                .text(messageDto.getText())
                .date(new Date())
                .userFrom(SecurityUtils.getCurrentUser())
                .userTo(null)
                .build();
        return messageStore.save(message);
    }

    @Override
    public Message saveDialogMessage(DialogMessageCreateDto messageDto) {
        Message message = Message.builder()
                .text(messageDto.getText())
                .date(new Date())
                .userFrom(SecurityUtils.getCurrentUser())
                .userTo(userStore
                        .findById(messageDto.getUserToId())
                        .orElseThrow(() -> new EntityNotFoundException("User with current id not found")))
                .build();
        return messageStore.save(message);
    }

    @Override
    public Message updateMessageText(MessageTextUpdateDto messageDto) {
        Message dbMessage = messageStore
                .findById(messageDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Message with current id not found"));

        if(!dbMessage.getUserFrom().equals(SecurityUtils.getCurrentUser())) {
            throw new PermissionDeniedException("You are not creator");
        }

        dbMessage.setText(messageDto.getText());
        return messageStore.save(dbMessage);
    }

    @Override
    public void deleteById(Integer messageId) {
        Message dbMessage = messageStore
                .findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message with current id not found"));

        if(!dbMessage.getUserFrom().equals(SecurityUtils.getCurrentUser())) {
            throw new PermissionDeniedException("You are not creator");
        }

        messageStore.deleteById(messageId);
    }
}
