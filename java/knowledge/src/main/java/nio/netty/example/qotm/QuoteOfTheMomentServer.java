package nio.netty.example.qotm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author zzt
 */
public class QuoteOfTheMomentServer {

  private static final int PORT = Integer.parseInt(System.getProperty("port", "7686"));

  public static void main(String[] args) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          // use UDP, so no need to use `ServerBootstrap`
          .channel(NioDatagramChannel.class)
          .option(ChannelOption.SO_BROADCAST, true)
          .handler(new QuoteOfTheMomentServerHandler());

      b.bind(PORT).sync().channel().closeFuture().await();
    } finally {
      group.shutdownGracefully();
    }
  }

}
