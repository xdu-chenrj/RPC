package org.serialize;

public interface Serializer {
    byte[] serialize(Object obj);
    <T> T deserialize(byte[] bytes);
}
