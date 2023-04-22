package org.remote.transport.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.remote.dto.RpcMessage;

import java.util.List;

public class RpcMessageDecoder extends MessageToMessageDecoder<RpcMessage> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, List<Object> list) throws Exception {

    }
}
