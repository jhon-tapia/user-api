package com.tapia.user.business.impl;

import com.tapia.user.business.UserService;
import com.tapia.user.dao.UserDao;
import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public Maybe<UserDto> createUser(UserDto userDto) {
        return Maybe.fromCallable(() -> this.setUserToken(userDto))
                .flatMap(userDao::createUser);
    }

    private UserDto setUserToken(UserDto userDto) {
        userDto.setToken(UUID.randomUUID().toString());
        return userDto;
    }
}
