package chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by bo.zhou1 on 2017/12/19.
 */
public class ChatServer {

    private static ChatServer instance;

    private ChatServer(){}

    public static ChatServer getInstance(){
        if (instance == null) {
            synchronized (ChatServer.class){
                if (instance == null) {
                    instance = new ChatServer();
                }
            }
        }
        return instance;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChatServerInitalizer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("ChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = null; // (7)
            try {
                f = b.bind(8080).sync();
                // 等待服务器  socket 关闭 。
                // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("ChatServer 关闭了");
        }
    }

    public static void main(String[] args){
        ChatServer.getInstance().start();
    }
}
