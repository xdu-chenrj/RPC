package org.remote.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RpcMessage {
    private byte messageType;
    private byte codecType;
    private byte compressType;
    private Integer requestId;
    private Object data;
}
