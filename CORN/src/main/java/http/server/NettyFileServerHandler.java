package http.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Jonbo
 * Date: 2017-12-24
 * Time: 21:54
 */
public class NettyFileServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static Logger logger = Logger.getLogger(NettyFileServerHandler.class);

    private HttpRequest request;

    private HttpResponse response;

    private boolean readingChunks;

    private final StringBuilder responseContent = new StringBuilder();

    private HttpPostRequestDecoder decoder;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn(responseContent.toString(), cause);
        ctx.channel().close();
    }
}
