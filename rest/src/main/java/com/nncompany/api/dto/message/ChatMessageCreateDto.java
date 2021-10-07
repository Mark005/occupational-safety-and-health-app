package com.nncompany.api.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageCreateDto {
    private Integer id;
    private String text;
}
