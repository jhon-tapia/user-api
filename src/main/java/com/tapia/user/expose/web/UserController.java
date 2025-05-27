package com.tapia.user.expose.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapia.user.business.UserService;
import com.tapia.user.exception.ExceptionDetail;
import com.tapia.user.expose.web.mapper.UserApiMapper;
import com.tapia.user.model.api.UserCreateRequest;
import com.tapia.user.model.api.UserCreateResponse;
import io.reactivex.Maybe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
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
  @Operation(summary = "Crear un nuevo usuario", description = "Endpoint de creación de nuevos usuarios")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                  content = @Content(schema = @Schema(implementation = UserCreateResponse.class))),
          @ApiResponse(responseCode = "400", description = "Parametros incorrectos",
                  content = @Content(schema = @Schema(implementation = ExceptionDetail.class))),
          @ApiResponse(responseCode = "503", description = "Servicio no disponible",
                  content = @Content(schema = @Schema(implementation = ExceptionDetail.class)))
  })
  @PostMapping(
      value = "/create",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Maybe<ResponseEntity<UserCreateResponse>> createUser(
          @Valid @RequestBody UserCreateRequest userCreateRequest) {

    return Maybe.fromCallable(() -> userApiMapper.toUserDto(userCreateRequest))
            .flatMap(userService::createUser)
            .doOnSuccess(userDto -> log.debug("User DTO: {}", new ObjectMapper().writeValueAsString(userDto)))
            .map(userApiMapper::toUserCreateResponse)
            .doOnSuccess(user -> log.debug("{}", new ObjectMapper().writeValueAsString(user)))
            .map(ResponseEntity::ok);
  }

}
