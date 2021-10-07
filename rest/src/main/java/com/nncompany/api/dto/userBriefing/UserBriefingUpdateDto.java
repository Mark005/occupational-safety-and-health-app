package com.nncompany.api.dto.userBriefing;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserBriefingUpdateDto {
    private Integer id;
    private Date lastDate;
}
