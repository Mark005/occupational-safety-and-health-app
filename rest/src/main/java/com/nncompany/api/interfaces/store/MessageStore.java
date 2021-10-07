package com.nncompany.api.interfaces.store;

import com.nncompany.api.model.entities.Message;
import com.nncompany.api.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MessageStore extends JpaRepository<Message, Integer> {

    @Query("SELECT m " +
            "FROM Message m " +
            "WHERE (m.userFrom =:userOne and m.userTo =:userTwo) or " +
            "(m.userFrom =:userTwo and m.userTo =:userOne)")
    Page<Message> findAllMessagesFromDialog(@Param("userOne") User userOne,
                                            @Param("userTwo") User userTwo,
                                            Pageable pageable);

    Page<Message> findAllByUserToIsNull(Pageable pageable);
}
