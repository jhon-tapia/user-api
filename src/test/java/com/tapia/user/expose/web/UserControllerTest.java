package com.tapia.user.expose.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tapia.user.business.UserService;
import com.tapia.user.expose.web.mapper.UserApiMapper;
import com.tapia.user.model.api.Phone;
import com.tapia.user.model.api.UserCreateRequest;
import com.tapia.user.model.api.UserCreateResponse;
import com.tapia.user.model.domain.PhoneDto;
import com.tapia.user.model.domain.UserDto;
import io.reactivex.Maybe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @MockBean
    private UserApiMapper userApiMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUser_successfulCreation_returnsOkResponse() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("test_password")
                .token("c3c79171-1c98-4374-8791-711c98c374a0")
                .phones(List.of(PhoneDto.builder()
                        .citycode("01")
                        .contrycode("51")
                        .number("123456789")
                        .build()))
                .build();

        UserCreateResponse response = new UserCreateResponse();
        response.setName("Juan Rodriguez");
        response.setEmail("juan@rodriguez.org");
        response.setPassword("test_password");
        response.setToken("c3c79171-1c98-4374-8791-711c98c374a0");
        response.setPhones(Collections.singletonList(new Phone("123456789", "01", "51")));

        when(userApiMapper.toUserDto(any())).thenReturn(userDto);
        when(userService.createUser(any())).thenReturn(Maybe.just(userDto));
        when(userApiMapper.toUserCreateResponse(any())).thenReturn(response);

        webTestClient.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n" +
                        "  \"name\": \"Juan Rodriguez\",\n" +
                        "  \"email\": \"juan@rodriguez.org\",\n" +
                        "  \"password\": \"test_password\",\n" +
                        "  \"phones\": [\n" +
                        "    {\n" +
                        "      \"number\": \"123456789\",\n" +
                        "      \"citycode\": \"01\",\n" +
                        "      \"contrycode\": \"51\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody().jsonPath("$.name").isEqualTo("Juan Rodriguez");
    }

    @Test
    public void createUser_invalidRequest_returnsBadRequest() throws Exception {
        webTestClient.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"email\": \"invalid-email\",\n" +
                        "  \"password\": \"123\",\n" +
                        "  \"phones\": []\n" +
                        "}")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").exists();
    }

    @Test
    public void createUser_serviceThrowsException_returnsInternalServerError() throws Exception {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("Juan Ramirez");
        request.setEmail("juan@mail.com");
        request.setPassword("password123");
        request.setPhones(Collections.singletonList(new Phone("123456789", "01", "51")));

        when(userApiMapper.toUserDto(any())).thenThrow(new RuntimeException("Service exception"));

        webTestClient.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n" +
                        "  \"name\": \"Juan Ramirez\",\n" +
                        "  \"email\": \"juan@mail.com\",\n" +
                        "  \"password\": \"password123\",\n" +
                        "  \"phones\": [\n" +
                        "    {\n" +
                        "      \"number\": \"123456789\",\n" +
                        "      \"citycode\": \"01\",\n" +
                        "      \"contrycode\": \"51\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}