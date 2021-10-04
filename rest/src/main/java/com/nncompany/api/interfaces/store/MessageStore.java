package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Message;
import com.nncompany.api.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageStore extends JpaRepository<Message, Integer> {

    Page<Message> findAllByUserFromAndUserTo(User userOne, User userTwo, Pageable pageable);

    Page<Message> findAllByUserToIsNull(Pageable pageable);
}
