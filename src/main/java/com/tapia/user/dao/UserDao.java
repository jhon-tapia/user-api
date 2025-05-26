package com.tapia.user.dao;

import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;

public interface UserDao {

    Maybe<UserDto> createUser(UserDto userDto);

}
