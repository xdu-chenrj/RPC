package framework.remote.transport.netty.codec;

import framework.enums.CompressTypeEnum;
import framework.enums.SerializationTypeEnum;
import framework.extension.ExtensionLoader;
import framework.remote.constants.RpcConstants;
import framework.remote.dto.RpcMessage;
import framework.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import framework.compress.Compress;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * custom protocol decoder
 * <p>
 * 0     1     2     3     4        5     6     7     8         9          10      11     12  13  14   15 16
 * +-----+-----+-----+-----+--------+----+----+----+------+-----------+-------+----- --+-----+-----+-------+
 * |   magic   code        |version | full length         | messageType| codec|compress|    RequestId       |
 * +-----------------------+--------+---------------------+-----------+-----------+-----------+------------+
 * |                                                                                                       |
 * |                                         body                                                          |
 * |                                                                                                       |
 * |                                        ... ...                                                        |
 * +-------------------------------------------------------------------------------------------------------+
 * 4B  magic code（魔法数）   1B version（版本）   4B full length（消息长度）    1B messageType（消息类型）
 * 1B compress（压缩类型） 1B codec（序列化类型）    4B  requestId（请求的Id）
 * body（object类型数据）
 * <p>
 * LengthFieldBasedFrameDecoder is a length-based decoder, used to solve TCP unpacking and sticking problems.
 */

@Slf4j
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) {
        try {
            byteBuf.writeBytes(RpcConstants.MAGIC_NUMBER);
            byteBuf.writeByte(RpcConstants.VERSION);
            // full length
            byteBuf.writerIndex(byteBuf.writerIndex() + 4);
            byte messageType = rpcMessage.getMessageType();
            byteBuf.writeByte(messageType);
            byteBuf.writeByte(rpcMessage.getSerializeType());
            byteBuf.writeByte(CompressTypeEnum.GZIP.getCode());
            byteBuf.writeInt(ATOMIC_INTEGER.getAndIncrement());
            // full length
            byte[] body = null;
            int fullLength = RpcConstants.HEAD_LENGTH;
            if (messageType != RpcConstants.HEARTBEAT_REQUEST_TYPE
                    && messageType != RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                String serializeName = SerializationTypeEnum.getName(rpcMessage.getSerializeType());
                log.info("serialize name: {}", serializeName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serializeName);
                body = serializer.serialize(rpcMessage.getData());

                String compressName = CompressTypeEnum.getName(rpcMessage.getCompressType());
                log.info("compress name: {}", compressName);
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension(compressName);
                body = compress.compress(body);
                fullLength += body.length;
            }
            if (body != null) {
                // TODO
                byteBuf.writeBytes(body);
            }
            int writeIndex = byteBuf.writerIndex();
            // TODO Verification required
            byteBuf.writerIndex(writeIndex - fullLength + RpcConstants.MAGIC_NUMBER.length + 1);
            byteBuf.writeInt(fullLength);
            byteBuf.writerIndex(writeIndex);
        } catch (Exception e) {
            log.error("encode request error");
        }
    }
}
