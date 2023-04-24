package org.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcErrorMessageEnum {
    SERVER_CAN_NOT_BE_FOUND("sever can't not be found");

    private final String message;
}
