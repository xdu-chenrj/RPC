package framework.serialize;

import framework.extension.SPI;

@SPI
public interface Serializer {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
