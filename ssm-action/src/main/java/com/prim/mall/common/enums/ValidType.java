package com.prim.mall.common.enums;

public enum ValidType {
    VALID_USERNAME("username"),
    VALID_EMAIL("email");
    private final String valid;

    ValidType(String valid) {
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }
}
