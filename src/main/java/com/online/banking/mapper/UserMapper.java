package com.online.banking.mapper;

import com.online.banking.dto.UserDto;
import com.online.banking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
