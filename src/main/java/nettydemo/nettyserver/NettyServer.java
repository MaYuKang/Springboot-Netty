package nettydemo.nettyserver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;
@Component
public class NettyServer {
    public void start(InetSocketAddress address){
        //bossGroup设置一个线程，用于处理连接请求和建立连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workGroup线程池大小默认值2*CPU核数，在连接建立之后处理IO请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
                         ServerBootstrap bootstrap = new ServerBootstrap()
                                 .group(bossGroup,workerGroup)
                                 //设置channelFactory
                                 .channel(NioServerSocketChannel.class)
                                 .localAddress(address)
                                 //配置childHandler，数据处理器
                                 .childHandler(new ServerChannelInitializer())
                                 .option(ChannelOption.SO_BACKLOG, 128)
                                 .childOption(ChannelOption.SO_KEEPALIVE, true);
                         // 绑定端口，开始接收进来的连接
                         ChannelFuture future = bootstrap.bind(address).sync();
                         System.out.println("Server start listen at :" + address.getPort());
                         //进入等待状态，当服务连接中断后才会进入后续操作，比如finally块的优雅关闭连接
                         future.channel().closeFuture().sync();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }finally {
                            bossGroup.shutdownGracefully();
                            workerGroup.shutdownGracefully();
                     }

    }


}
