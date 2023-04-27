package org.remote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.common.enums.RpcResponseCodeEnum;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID = 200967137377196021L;
    private String requestId;
    private Integer code;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponseBuilder<T>().code(RpcResponseCodeEnum.SUCCESS.getCode()).message(RpcResponseCodeEnum.SUCCESS.getMessage()).build();
        response.setRequestId(requestId);
        if (null != data) response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCodeEnum rpcResponseCodeEnum) {
        RpcResponse<T> response = new RpcResponseBuilder<T>().code(rpcResponseCodeEnum.getCode()).message(rpcResponseCodeEnum.getMessage()).build();
        return response;
    }
}
