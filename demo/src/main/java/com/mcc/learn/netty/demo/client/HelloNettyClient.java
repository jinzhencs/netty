package com.mcc.learn.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * Created by Mingchenchen on 2017/9/21
 */
public class HelloNettyClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup);
            client.channel(NioSocketChannel.class);
            client.option(ChannelOption.SO_KEEPALIVE, true);
            client.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HelloNettyClientHandler());
                }
            });

            ChannelFuture future = client.connect(host, port).sync();

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private static class HelloNettyClientHandler extends ChannelHandlerAdapter {
        // 接收server端的消息，并打印出来
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf inBuf = (ByteBuf) msg;
            System.out.println("client receive: " + inBuf.toString(CharsetUtil.US_ASCII));
            inBuf.release();
        }

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            String msg = "client say: are you ok?";
            ByteBuf outBuf = ctx.alloc().buffer(4 * msg.length());
            outBuf.writeBytes(msg.getBytes());
            ctx.write(outBuf);
            ctx.flush();
        }
    }
}
