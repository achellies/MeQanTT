package com.albin.mqtt;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.albin.mqtt.message.ConnAckMessage;
import com.albin.mqtt.message.Message;
import com.albin.mqtt.message.PublishMessage;

public class MqttMessageHandler extends SimpleChannelHandler {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		System.out.println("Caught exception: " + e.getCause());
		e.getChannel().close();
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		System.out.println("Disconnected!");
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		handleMessage((Message) e.getMessage());
	}
	
	private void handleMessage(Message msg) {
		if (msg == null) {
			return;
		}
		switch (msg.getType()) {
		case CONNACK:
			handleMessage((ConnAckMessage) msg);
			break;
		case PUBLISH:
			handleMessage((PublishMessage) msg);
			break;
		default:
			break;
		}
	}

	private void handleMessage(ConnAckMessage msg) {
		System.out.println("Connected!");
	}

	private void handleMessage(PublishMessage msg) {
		System.out.println("PUBLISH (" + msg.getTopic() + "): "
				+ msg.getDataAsString());
	}


}
