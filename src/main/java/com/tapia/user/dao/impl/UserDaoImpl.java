package com.tapia.user.dao.impl;

import com.tapia.user.exception.UserManagerException;
import com.tapia.user.dao.UserDao;
import com.tapia.user.dao.mapper.UserDaoMapper;
import com.tapia.user.dao.repository.UserRepository;
import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private UserRepository userRepository;

    private UserDaoMapper userDaoMapper;

    @Override
    public Maybe<UserDto> createUser(UserDto userDto) {
        return Maybe.fromCallable(() -> userDaoMapper.toUserEntity(userDto))
                .map(userRepository::save)
                .map(userDaoMapper::toUserDto)
                .onErrorResumeNext(this::validateEmailConstraintException)
                .doOnError(throwable -> log.error("Error al crear el usuario {}", userDto, throwable))
                .doOnSuccess(user -> log.info("Se creó el usuario {}", user));
    }

    private Maybe<UserDto> validateEmailConstraintException(Throwable throwable) {
        if(throwable instanceof DataIntegrityViolationException){
            return Maybe.error(new UserManagerException("El correo ya fue registrado", HttpStatus.CONFLICT));
        }
        return Maybe.error(throwable);
    }

}
