package org.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.common.RPCResponseCodeConstant;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCResponse<T> implements Serializable {

    private static final long serialVersionUID = 200967137377196021L;
    private String requestId;
    private Integer code;
    private String message;
    private T data;

    public static <T> RPCResponse<T> success(T data, String requestId) {
        RPCResponse<T> response = new RPCResponseBuilder<T>().code(RPCResponseCodeConstant.SUCCESS.getCode()).message(RPCResponseCodeConstant.SUCCESS.getMessage()).build();
        response.setRequestId(requestId);
        if (null != data) response.setData(data);
        return response;
    }

    public static <T> RPCResponse<T> fail(Integer code, String message) {
        RPCResponse<T> response = new RPCResponseBuilder<T>().code(code).message(message).build();
        return response;
    }
}
