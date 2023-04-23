package org.serialize.hessian;

import org.common.extension.SPI;
import org.serialize.Serializer;

@SPI
public class HessianSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }


}
