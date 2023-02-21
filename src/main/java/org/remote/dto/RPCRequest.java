package org.remote.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCRequest implements Serializable {
    private static final long serialVersionUID = 8263906140143559604L;

    private String interfaceName;

    private String methodName;

    private Object[] parameters;

    private Class<?>[] paramTypes;

    private String versionId;

}
