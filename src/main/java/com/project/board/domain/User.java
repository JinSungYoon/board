package com.project.board.domain;

import com.project.board.enumeration.UserRoleCode;
import com.project.board.utils.LoginIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long userId;
    private String loginId;
    private String password;

    private String salt;
    private String nickName;
    private UserRoleCode userRoleCode;
    private char activeYn;
    private char deleteYn;
    private LocalDateTime  createDate;
    private LocalDateTime updateDate;

    // Builder 통해 User 생성
    public User(UserBuilder builder) {
        this.userId         = builder.userId;
        this.loginId        = builder.loginId;
        this.password       = builder.password;
        this.salt           = builder.salt;
        this.nickName       = builder.nickName;
        this.userRoleCode   = builder.userRoleCode;
        this.activeYn       = builder.activeYn;
        this.deleteYn       = builder.deleteYn;
        this.createDate     = builder.createDate;
        this.updateDate     = builder.updateDate;
    }

    // ID와 Password 있는 Builder(Member)
    public static UserBuilder memberBuilder(String loginId,String password,String salt,String nickName) {
        return new UserBuilder()
                .loginId(loginId)
                .password(password)
                .salt(salt)
                .nickName(nickName)
                .userRoleCode(UserRoleCode.MEB)
                .activeYn('Y')
                .deleteYn('N')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now());
    }


    // ID와 Password 없는 Builder(Guest)
    public static UserBuilder guestBuilder(String nickName) {
        return new UserBuilder()
                .loginId(LoginIdGenerator.generateLoginId())
                .password(null)
                .salt(null)
                .nickName(nickName)
                .userRoleCode(UserRoleCode.GST)
                .activeYn('Y')
                .deleteYn('N')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return activeYn == user.activeYn &&
                deleteYn == user.deleteYn &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(loginId, user.loginId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(nickName, user.nickName) &&
                userRoleCode == user.userRoleCode &&
                Objects.equals(createDate, user.createDate) &&
                Objects.equals(updateDate, user.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, loginId, password, salt, nickName, userRoleCode, activeYn, deleteYn, createDate, updateDate);
    }

}
