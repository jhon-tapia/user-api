package com.tapia.user.dao.impl;

import com.tapia.user.dao.UserDao;
import com.tapia.user.dao.mapper.UserDaoMapper;
import com.tapia.user.dao.repository.UserRepository;
import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private UserRepository userRepository;

    private UserDaoMapper userDaoMapper;

    @Override
    public Maybe<UserDto> createUser(UserDto userDto) {
        return Maybe.fromCallable(() -> userDaoMapper.toUserEntity(userDto))
                .map(userRepository::save)
                .map(userDaoMapper::toUserDto);
    }

}
