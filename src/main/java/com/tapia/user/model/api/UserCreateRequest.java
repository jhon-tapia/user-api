package com.tapia.user.model.api;

import com.tapia.user.validation.ClaveValida;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Solicitud para crear un nuevo usuario")
public class UserCreateRequest {

  @Schema(description = "Nombre completo del usuario", example = "Jhon Tapia", required = true)
  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String name;

  @Schema(description = "Correo del usuario", example = "jhontapia@example.com", required = true)
  @NotBlank(message = "El correo es obligatorio")
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de correo electrónico inválido")
  private String email;

  @ClaveValida(message = "La contraseña debe cumplir con lo siguiente: " +
          "Debe contener al menos una letra minúscula," +
          "Debe contener al menos una letra mayúscula," +
          "Debe contener al menos un número," +
          "Debe contener al menos uno de estos caracteres especiales: @, $, !, %, *, ?, &," +
          "Debe contener solo letras (mayúsculas y minúsculas), números y los caracteres especiales mencionados," +
          "Debe tener una longitud mínima de 8 caracteres")
  @Schema(description = "Contraseña del usuario", example = "password2$Ab", required = true, minLength = 8, maxLength = 20)
  @NotBlank(message = "El password es obligatorio")
  @Size(min = 8, max = 20, message = "El password debe tener entre 8 y 20 caracteres")
  private String password;

  @Schema(description = "Números de teléfono", required = true)
  @Valid
  @NotEmpty(message = "Debe incluir al menos un teléfono")
  private List<Phone> phones;

}
