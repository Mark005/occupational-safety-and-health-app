package com.nncompany.impl.service;

import com.nncompany.api.interfaces.services.MessageService;
import com.nncompany.api.interfaces.store.MessageStore;
import com.nncompany.api.model.entities.Message;
import com.nncompany.api.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageStore messageStore;

    @Override
    public Message get(int id) {
        return messageStore.getById(id);
    }

    @Override
    public List<Message> getWithPagination(Integer page, Integer pageSize) {
        return messageStore
                .findAll(PageRequest.of(page, pageSize))
                .getContent();
    }

    @Override
    public List<Message> getDialogWithPagination(User userOne, User userTwo, Integer page, Integer pageSize) {
        return messageStore
                .findAllByUserFromAndUserTo(userOne, userTwo, PageRequest.of(page, pageSize))
                .getContent();
    }

    @Override
    public List<Message> getChatWithPagination(Integer page, Integer pageSize) {
        return messageStore
                .findAllByUserToIsNull(PageRequest.of(page, pageSize))
                .getContent();
    }

    @Override
    public void save(Message message) {
        messageStore.save(message);
    }

    @Override
    public void update(Message message) {
        messageStore.save(message);
    }

    @Override
    public void delete(Message message) {
        messageStore.delete(message);
    }
}
