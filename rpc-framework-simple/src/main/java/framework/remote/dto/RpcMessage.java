package framework.remote.dto;


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
    private byte serializeType;
    private byte compressType;
    private Integer requestId;
    private Object data;
}
