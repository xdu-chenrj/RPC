package org.serialize.kyro;

import org.common.extension.SPI;
import org.serialize.Serializer;

@SPI
public class KryoSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }


}
