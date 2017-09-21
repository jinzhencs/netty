package com.mcc.learn.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Mingchenchen on 2017/9/20
 *
 * 简单的Netty Server
 */
public class HelloNettyServer implements Runnable {
    private int port;

    public HelloNettyServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //接受连接的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //执行处理逻辑的线程组

        System.out.println("server starting, port: " + port);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap = bootstrap.group(bossGroup, workerGroup);

            bootstrap = bootstrap.channel(NioServerSocketChannel.class);

            bootstrap = bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardServerHandler());
                }
            });

            bootstrap = bootstrap.option(ChannelOption.SO_BACKLOG, 128);

            bootstrap = bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = bootstrap.bind(port).sync();

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * server端处理逻辑: 仅仅打印一下
     */
    private static class DiscardServerHandler extends ChannelHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf inputBuf = (ByteBuf) msg;

            System.out.println("server receive: " + inputBuf.toString(CharsetUtil.US_ASCII));

            inputBuf.release();

            String response = "server response : I am ok!";
            ByteBuf outBuf = ctx.alloc().buffer(4 * response.length());
            outBuf.writeBytes(response.getBytes());
            ctx.write(outBuf);
            ctx.flush();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
