package org.remote.transport.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.remote.dto.RPCMessage;

import java.util.List;

public class RPCMessageDecoder extends MessageToMessageDecoder<RPCMessage> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, RPCMessage rpcMessage, List<Object> list) throws Exception {

    }
}
