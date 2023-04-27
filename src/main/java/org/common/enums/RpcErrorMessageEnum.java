package org.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcErrorMessageEnum {
    SERVER_CAN_NOT_BE_FOUND("sever can't not be found"),
    SERVICE_INVOCATION_FAILURE("service invocation failure"),
    REQUEST_NOT_MATCH_RESPONSE("request not match response");

    private final String message;
}
