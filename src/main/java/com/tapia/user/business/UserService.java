package com.tapia.user.business;

import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;

public interface UserService {

    Maybe<UserDto> createUser(UserDto userDto);

}
