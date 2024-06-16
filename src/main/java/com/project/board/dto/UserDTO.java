package com.project.board.dto;

import com.project.board.enumeration.UserRoleCode;
import com.project.board.utils.LoginIdGenerator;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    @Size(max = 15, message = "Login ID cannot be longer than 15 characters")
    private String loginId;
    @Size(max = 20, message = "Password cannot be longer than 20 characters")
    private String password;
    @Size(max = 50, message = "Nick Name cannot be longer than 50 characters")
    private String nickName;
    private UserRoleCode userRoleCode;
    private char activeYn;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // Builder 통해 User 생성
    public UserDTO(UserDTO.UserDTOBuilder builder) {
        this.loginId = builder.loginId;
        this.password = builder.password;
        this.nickName = builder.nickName;
        this.userRoleCode = builder.userRoleCode;
        this.activeYn = builder.activeYn;
        this.createDate = builder.createDate;
        this.updateDate = builder.updateDate;
    }

    // ID와 Password 있는 Builder(Member)
    public static UserDTO.UserDTOBuilder memberBuilder(String loginId, String password, String nickName) {
        return new UserDTO.UserDTOBuilder()
                .loginId(loginId)
                .password(password)
                .nickName(nickName)
                .userRoleCode(UserRoleCode.MEB)
                .activeYn('Y')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now());
    }


    // ID와 Password 없는 Builder(Guest)
    public static UserDTO.UserDTOBuilder guestBuilder(String nickName) {
        return new UserDTO.UserDTOBuilder()
                .loginId(LoginIdGenerator.generateLoginId())
                .password("")
                .nickName(nickName)
                .userRoleCode(UserRoleCode.GST)
                .activeYn('Y')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDto = (UserDTO) o;
        return activeYn == userDto.activeYn &&
                Objects.equals(loginId, userDto.loginId) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(nickName, userDto.nickName) &&
                userRoleCode == userDto.userRoleCode &&
                Objects.equals(createDate, userDto.createDate) &&
                Objects.equals(updateDate, userDto.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, password, nickName, userRoleCode, activeYn, createDate, updateDate);
    }

}
