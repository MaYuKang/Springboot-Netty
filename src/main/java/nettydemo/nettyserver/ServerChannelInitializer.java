package nettydemo.nettyserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    //对接收的channel进行初始化
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        /*channel.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));*/
        channel.pipeline().addLast(new ObjectEncoder());
        channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                ClassResolvers.cacheDisabled(null)));
        channel.pipeline().addLast(new ServerHandler());
    }
}