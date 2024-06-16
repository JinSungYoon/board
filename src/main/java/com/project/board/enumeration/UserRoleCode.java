package com.project.board.enumeration;

public enum UserRoleCode {
    MEB("MEMBER"),
    GST("GUEST");

    private final String code;

    UserRoleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static UserRoleCode fromCode(String code) {
        for (UserRoleCode role : UserRoleCode.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }

}
