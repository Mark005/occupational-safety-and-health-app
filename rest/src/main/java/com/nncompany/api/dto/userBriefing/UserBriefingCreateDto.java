package com.nncompany.api.dto.userBriefing;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserBriefingCreateDto {
    private Integer userId;
    private Integer briefingId;
    private Date lastDate;
}
