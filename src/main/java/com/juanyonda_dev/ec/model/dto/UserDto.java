package com.juanyonda_dev.ec.model.dto;

import lombok.*;


@Data
@ToString
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String password;
}
