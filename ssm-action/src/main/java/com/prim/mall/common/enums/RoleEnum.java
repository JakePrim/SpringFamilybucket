package com.prim.mall.common.enums;

public enum RoleEnum {
    /**
     * 标志为普通用户
     */
    ROLE_CUSTOMER(0, "普通用户"),
    /**
     * 标志为管理员
     */
    ROLE_ADMIN(1, "管理员");

    private final Integer code;
    private final String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
