package com.tapia.user.dao.impl;

import com.tapia.user.dao.mapper.UserDaoMapper;
import com.tapia.user.dao.repository.UserRepository;
import com.tapia.user.exception.UserManagerException;
import com.tapia.user.model.api.Phone;
import com.tapia.user.model.domain.PhoneDto;
import com.tapia.user.model.domain.UserDto;
import com.tapia.user.model.entity.PhoneEntity;
import com.tapia.user.model.entity.UserEntity;
import io.reactivex.Maybe;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDaoImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDaoMapper userDaoMapper;

    @InjectMocks
    private UserDaoImpl userDaoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserSuccess() {
        UserDto inputDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneDto.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();
        UserDto outputDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneDto.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();
        UserEntity userEntity = UserEntity.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneEntity.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();

        when(userDaoMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userDaoMapper.toUserDto(any())).thenReturn(outputDto);

        Maybe<UserDto> result = userDaoImpl.createUser(inputDto);

        UserDto resultDto = result.blockingGet();
        assertEquals(outputDto.getName(), resultDto.getName());
        assertEquals(outputDto.getEmail(), resultDto.getEmail());
        assertEquals(outputDto.getPassword(), resultDto.getPassword());
        assertEquals(outputDto.getPhones(), resultDto.getPhones());
        assertEquals(outputDto.getToken(), resultDto.getToken());
        assertEquals(outputDto.getCreated(), resultDto.getCreated());
        assertEquals(outputDto.getModified(), resultDto.getModified());
        assertEquals(outputDto.getLastLogin(), resultDto.getLastLogin());
        assertEquals(outputDto.getIsactive(), resultDto.getIsactive());

        verify(userDaoMapper).toUserEntity(inputDto);
        verify(userRepository).save(userEntity);
        verify(userDaoMapper).toUserDto(userEntity);
    }

    @Test
    void testCreateUserEmailAlreadyExists() {

        UserDto inputDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneDto.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();
        UserEntity userEntity = UserEntity.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneEntity.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();

        when(userDaoMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userDaoImpl.createUser(inputDto).blockingGet();
        });

        Assert.assertThat(exception.getMessage(), org.hamcrest.CoreMatchers.containsString("El correo ya fue registrado"));
        verify(userDaoMapper).toUserEntity(inputDto);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testCreateUserUnexpectedError() {

        UserDto inputDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .build();
        UserEntity userEntity = new UserEntity();
        RuntimeException unexpectedException = new RuntimeException("Unexpected error");

        when(userDaoMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any())).thenThrow(unexpectedException);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userDaoImpl.createUser(inputDto).blockingGet();
        });

        assertEquals("Unexpected error", exception.getMessage());
        verify(userDaoMapper).toUserEntity(inputDto);
        verify(userRepository).save(userEntity);
    }
}