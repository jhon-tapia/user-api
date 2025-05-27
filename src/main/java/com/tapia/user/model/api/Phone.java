package com.tapia.user.model.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información del número de teléfono")
public class Phone {

  @Schema(description = "Numero de Teléfono", example = "123456789")
  @NotBlank(message = "El numero de telefono es obligatorio")
  @Pattern(regexp = "^[0-9]{6,15}$", message = "Ingrese un numero de telefono valido")
  private String number;

  @Schema(description = "Código de ciudad", example = "1")
  @NotBlank(message = "El código de ciudad es obligatorio")
  @Size(min = 1, max = 2, message = "El código de ciudad debe tener entre 1 y 2 caracteres")
  private String citycode;

  @Schema(description = "Código de País", example = "51")
  @NotBlank(message = "El código de pais es obligatorio")
  @Size(min = 1, max = 5, message = "El codigo de pais debe entre 1 y 5 caracteres")
  private String contrycode;

}
