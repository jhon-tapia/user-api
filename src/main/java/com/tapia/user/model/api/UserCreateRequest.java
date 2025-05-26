package com.tapia.user.model.api;

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
public class UserCreateRequest {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String name;

  @NotBlank(message = "El correo es obligatorio")
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Formato de correo electrónico inválido")
  private String email;

  @NotBlank(message = "El password es obligatorio")
  @Size(min = 8, max = 20, message = "El password debe tener entre 8 y 20 caracteres")
  private String password;

  @Valid
  @NotEmpty(message = "Debe incluir al menos un teléfono")
  private List<Phone> phones;

}
