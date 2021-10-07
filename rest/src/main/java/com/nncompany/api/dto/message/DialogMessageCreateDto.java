package com.nncompany.api.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DialogMessageCreateDto {
    private String text;
    private Integer userToId;
}
