package com.todaytrend.authservice.domain.enum_;

public enum UserType {
    LOCAL("LOCAL"),
    SOCIAL("SOCIAL");

    private final String value;

    UserType (String value) {
        this.value = value;
    }

}
