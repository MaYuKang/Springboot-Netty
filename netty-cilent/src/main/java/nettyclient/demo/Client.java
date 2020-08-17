package nettyclient.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import nettydemo.DO.User;

public class Client {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8087"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("马宇康");
        user.setAge(21);
        sendMessage(user);
    }
    public static void sendMessage(User content) throws InterruptedException{
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientChannelInitializer());
            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().writeAndFlush(content);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}