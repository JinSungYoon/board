package com.project.board.mapper;


import com.project.board.domain.User;
import com.project.board.dto.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .nickName(user.getNickName())
                .userRoleCode(user.getUserRoleCode())
                .activeYn(user.getActiveYn())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }

    public static User toDomain(UserDTO userDto) {
        return User.builder()
                .loginId(userDto.getLoginId())
                .password(userDto.getPassword())
                .nickName(userDto.getNickName())
                .userRoleCode(userDto.getUserRoleCode())
                .activeYn(userDto.getActiveYn())
                .createDate(userDto.getCreateDate())
                .updateDate(userDto.getUpdateDate())
                .build();
    }
}