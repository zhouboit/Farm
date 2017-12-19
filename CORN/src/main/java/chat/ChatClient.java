package chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by bo.zhou1 on 2017/12/19.
 */
public class ChatClient {

    private static ChatClient client;

    private ChatClient(){}

    public static ChatClient getInstance(){
        if (client == null) {
            synchronized (ChatClient.class){
                if (client == null) {
                    client = new ChatClient();
                }
            }
        }
        return client;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitalizer());
            Channel channel = bootstrap.connect("10.10.79.49", 8080).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        ChatClient.getInstance().start();

    }
}
