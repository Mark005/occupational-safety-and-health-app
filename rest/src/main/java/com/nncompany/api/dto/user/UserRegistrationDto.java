package com.nncompany.api.dto.user;

import com.nncompany.api.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserRegistrationDto {
    private String login;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private String profession;
    private Integer certificateNumber;
    private Date dateEmployment;
}
