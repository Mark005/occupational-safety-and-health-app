package com.nncompany.api.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChatMessageDto {
    private Integer id;
    private String text;
    private Date date;
    private Integer userFromId;
}
