package com.tapia.user.business.impl;

import com.tapia.user.dao.UserDao;
import com.tapia.user.model.domain.PhoneDto;
import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_SuccessfullyCreatesUser() {
        UserDto inputUserDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .phones(List.of(PhoneDto.builder()
                                .citycode("1")
                                .contrycode("51")
                                .number("123456789")
                        .build()))
                .build();

        UserDto savedUserDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .token("9d2ececc-ae01-4325-aece-ccae01d3253e")
                .phones(List.of(PhoneDto.builder()
                        .citycode("1")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();

        when(userDao.createUser(any(UserDto.class))).thenReturn(Maybe.just(savedUserDto));

        Maybe<UserDto> result = userService.createUser(inputUserDto);

        UserDto actualUser = result.blockingGet();
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getToken()).isNotNull();
        assertThat(actualUser.getName()).isEqualTo(inputUserDto.getName());
        assertThat(actualUser.getEmail()).isEqualTo(inputUserDto.getEmail());
        assertThat(actualUser.getPassword()).isNotNull();
        assertThat(actualUser.getPhones()).isNotEmpty();
        verify(userDao, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void testCreateUser_ReturnsEmptyWhenDaoFails() {
        UserDto inputUserDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .build();

        when(userDao.createUser(any(UserDto.class))).thenReturn(Maybe.empty());

        Maybe<UserDto> result = userService.createUser(inputUserDto);

        assertThat(result.isEmpty().blockingGet()).isTrue();
        verify(userDao, times(1)).createUser(any(UserDto.class));
    }

}