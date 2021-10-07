package com.nncompany.api.interfaces.services;

import com.nncompany.api.dto.message.ChatMessageCreateDto;
import com.nncompany.api.dto.message.DialogMessageCreateDto;
import com.nncompany.api.dto.message.MessageTextUpdateDto;
import com.nncompany.api.model.entities.Message;
import org.springframework.data.domain.Page;

public interface MessageService {

    Page<Message> getDialogWithUser(Integer userId, Integer page, Integer pageSize);

    Page<Message> getChatWithPagination(Integer page, Integer pageSize);

    Message saveChatMessage(ChatMessageCreateDto message);

    Message saveDialogMessage(DialogMessageCreateDto messageDto);

    Message updateMessageText(MessageTextUpdateDto messageDto);

    void deleteById(Integer messageId);

    Message getChatMessageById(Integer id);

    Message getDialogMessageById(Integer messageId);
}
