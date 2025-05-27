package com.tapia.user.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Modelo que representa los detalles de una excepción")
public class ExceptionDetail {

    @Schema(description = "Estado HTTP de la respuesta (opcional)", example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(description = "Código de error específico (opcional)", example = "ERR-001")
    private String errorCode;

    @Schema(description = "Mensaje descriptivo del error", example = "El correo electrónico ya está registrado", required = true)
    private String message;
}
