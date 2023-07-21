package dev.gb.webplayerserver.mappers.user;

import dev.gb.webplayerserver.dtos.UserDto;
import dev.gb.webplayerserver.mappers.DtoMapper;
import dev.gb.webplayerserver.models.users.User;

public class UserDtoMapper implements DtoMapper<User, UserDto> {
    private static final UserDtoMapper instance = new UserDtoMapper();
    private UserDtoMapper(){}
    @Override
    public User fromDto(UserDto userDto) {
        return new User()
                .withUsername(userDto.getUsername())
                .withPassword(userDto.getPassword())
                .withUserType(userDto.getUserType())
                .withEmail(userDto.getEmail())
                .buildUser();
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto()
                .withEmail(user.getEmail())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withUserType(user.getUserType())
                .buildUserDto();
    }

    public static User fromDtoToUser(UserDto userDto){
        return instance.fromDto(userDto);
    }

    public static UserDto fromUserDto(User user){
        return instance.toDto(user);
    }
}
