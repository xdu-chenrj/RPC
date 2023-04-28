package framework.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import framework.exception.SerializationException;
import framework.extension.SPI;
import framework.remote.dto.RpcRequest;
import framework.remote.dto.RpcResponse;
import framework.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KryoSerializer implements Serializer {

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Output out = new Output(byteArrayOutputStream);
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(out, obj);
            kryoThreadLocal.remove();
            return out.toBytes();
        } catch (IOException e) {
            throw new SerializationException("serialization failed");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (IOException e) {
            throw new SerializationException("deserialization failed");
        }
    }
}
