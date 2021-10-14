package com.nncompany.api.dto.userBriefing;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBriefingCreateDto {
    private Integer userId;
    private Integer briefingId;
    private LocalDateTime lastDate;
}
