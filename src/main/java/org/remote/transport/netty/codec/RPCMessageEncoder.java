package org.remote.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.remote.dto.RPCMessage;

public class RPCMessageEncoder extends MessageToByteEncoder<RPCMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RPCMessage rpcMessage, ByteBuf byteBuf) throws Exception {

    }
}
