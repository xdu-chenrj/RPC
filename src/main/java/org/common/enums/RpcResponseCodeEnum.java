package org.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RpcResponseCodeEnum {
    SUCCESS(200, "remote call succeeded"),
    FAIL(500, "remote call failed");

    private Integer code;
    private String message;
}

