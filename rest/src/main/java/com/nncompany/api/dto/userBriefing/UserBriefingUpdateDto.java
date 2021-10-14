package com.nncompany.api.dto.userBriefing;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBriefingUpdateDto {
    private Integer id;
    private LocalDateTime lastDate;
}
