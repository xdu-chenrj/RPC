package org.common.exception;

import org.common.enums.RpcErrorMessageEnum;

public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum errorMessageEnum, String detail) {
        super(errorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RpcException(RpcErrorMessageEnum errorMessageEnum) {
        super(errorMessageEnum.getMessage());
    }
}
