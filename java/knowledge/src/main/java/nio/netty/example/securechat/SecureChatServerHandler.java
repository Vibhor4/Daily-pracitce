package nio.netty.example.securechat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.InetAddress;

/**
 * @author zzt
 */
@Sharable
public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

  // all remote peers
  private static final ChannelGroup channels = new DefaultChannelGroup(
      GlobalEventExecutor.INSTANCE);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // Once session is secured, send a greeting and register the channel to the global channel
    // list so the channel received the messages from others.
    ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
        (GenericFutureListener<Future<Channel>>) future -> {
          ctx.writeAndFlush(
              "Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
          ctx.writeAndFlush(
              "Your session is protected by " +
                  ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                  " cipher suite.\n");

          channels.add(ctx.channel());
        }
    );
  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    for (Channel c : channels) {
      if (c != ctx.channel()) {
        c.writeAndFlush("[" + ctx.channel().remoteAddress() + "]" + msg + '\n');
      } else {
        c.writeAndFlush("[you] " + msg + '\n');
      }
    }

    if ("bye".equals(msg.toLowerCase())) {
      ctx.close();
    }
  }
}
