package org.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCResponse<T> implements Serializable {

    private String requestId;
    private Integer code;
    private String message;
    private T data;

    public static <T> RPCResponse<T> success(T data, String requestId) {
        RPCResponse<T> response = new RPCResponseBuilder<T>().build();
        if(null != data) response.setData(data);
        return response;
    }
    public static <T> RPCResponse<T> fail(Integer code, String message) {
        RPCResponse<T> response = new RPCResponseBuilder<T>().build();
        return response;
    }
}
