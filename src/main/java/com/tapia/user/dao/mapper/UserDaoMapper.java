package com.tapia.user.dao.mapper;

import com.tapia.user.model.domain.UserDto;
import com.tapia.user.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDaoMapper {

    UserEntity toUserEntity(UserDto userDto);

    UserDto toUserDto(UserEntity userEntity);
}