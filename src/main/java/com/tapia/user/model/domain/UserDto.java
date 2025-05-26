package com.tapia.user.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private String name;

  private String email;

  private String password;

  private List<PhoneDto> phones;

  private LocalDateTime created;

  private LocalDateTime modified;

  private LocalDateTime lastLogin;

  private String token;

  private Boolean isactive;

}
