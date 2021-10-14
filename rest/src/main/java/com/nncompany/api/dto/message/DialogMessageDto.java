package com.nncompany.api.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class DialogMessageDto {
    private Integer id;
    private String text;
    private LocalDateTime date;
    private Integer userFromId;
    private Integer userToId;
}
