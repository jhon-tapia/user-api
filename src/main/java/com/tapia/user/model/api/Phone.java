package com.tapia.user.model.api;

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
public class Phone {

  @NotBlank(message = "El numero de telefono es obligatorio")
  @Pattern(regexp = "^[0-9]{6,15}$", message = "Ingrese un numero de telefono valido")
  private String number;

  @NotBlank(message = "El código de ciudad es obligatorio")
  @Size(min = 1, max = 2, message = "El código de ciudad debe tener entre 1 y 2 caracteres")
  private String citycode;

  @NotBlank(message = "El código de pais es obligatorio")
  @Size(min = 1, max = 5, message = "El codigo de pais debe entre 1 y 5 caracteres")
  private String contrycode;

}
