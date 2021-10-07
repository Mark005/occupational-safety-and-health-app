package com.nncompany.api.dto.user;

import com.nncompany.api.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDto {
    private Integer id;
    private String name;
    private String surname;
    private Gender gender;
    private String profession;
    private Integer certificateNumber;
}
