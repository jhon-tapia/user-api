package com.tapia.user.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateResponse {

    @Schema(description = "Mensaje de respuesta", example = "Usuario creado exitosamente")
    private String message;

    @Schema(description = "Nombre del usuario", example = "Juan Rodriguez")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "juan@rodriguez.org")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "hunter2")
    private String password;

    @Schema(description = "Lista de teléfonos del usuario")
    private List<Phone> phones;

    @Schema(description = "Fecha de creación", example = "2023-10-24T10:30:00")
    private LocalDateTime created;

    @Schema(description = "Fecha de modificación", example = "2023-10-24T10:30:00")
    private LocalDateTime modified;

    @Schema(description = "Fecha del último inicio de sesión", example = "2023-10-24T10:30:00")
    private LocalDateTime lastLogin;

    @Schema(description = "Token de autenticación", example = "d0d094cc-d6d7-49fc-9094-ccd6d779fc17")
    private String token;

    @Schema(description = "Indica si el usuario está activo", example = "true")
    private Boolean isactive;

}
