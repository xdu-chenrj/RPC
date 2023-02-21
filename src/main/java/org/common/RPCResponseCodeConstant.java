package org.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RPCResponseCodeConstant {
    SUCCESS(200, "Remote call succeeded"),

    FAIL(500, "Remote call failed");

    private Integer code;
    private String message;
}

