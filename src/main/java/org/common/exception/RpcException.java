package org.common.exception;

import org.common.enums.RpcErrorMessageEnum;

public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum errorMessageEnum, String detail) {
        super(errorMessageEnum.getMessage() + ":" + detail);
    }

}
