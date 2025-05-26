package com.tapia.user.expose.web;

import com.tapia.user.business.UserService;
import com.tapia.user.expose.web.mapper.UserApiMapper;
import com.tapia.user.model.api.UserCreateRequest;
import com.tapia.user.model.api.UserCreateResponse;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/user/")
public class UserController {

  private UserService userService;

  private UserApiMapper userApiMapper;

  /**
   * Crear un nuevo usuario.
   * @param userCreateRequest
   * @return UserCreateResponse
   */
  @PostMapping(
      value = "/create",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Maybe<ResponseEntity<UserCreateResponse>> createUser(
          @Valid @RequestBody UserCreateRequest userCreateRequest) {

    return Maybe.fromCallable(() -> userApiMapper.toUserDto(userCreateRequest))
            .flatMap(userService::createUser)
            .map(userApiMapper::toUserCreateResponse)
            .map(ResponseEntity::ok);
  }

}
