package com.tapia.user.expose.web.mapper;

import com.tapia.user.model.api.UserCreateRequest;
import com.tapia.user.model.api.UserCreateResponse;
import com.tapia.user.model.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface UserApiMapper {

    UserDto toUserDto(UserCreateRequest userCreateRequest);

    UserCreateResponse toUserCreateResponse(UserDto userDto);
}