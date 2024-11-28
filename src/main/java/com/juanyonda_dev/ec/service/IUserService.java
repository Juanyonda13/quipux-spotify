package com.juanyonda_dev.ec.service;

import com.juanyonda_dev.ec.model.dto.UserDto;
import com.juanyonda_dev.ec.model.entity.User;

import java.util.Optional;

public interface IUserService {
    User save(UserDto userDto);
    Optional<User> findByEmail(String email);
}
