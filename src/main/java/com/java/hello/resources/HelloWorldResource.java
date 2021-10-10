package com.java.hello.resources;

import java.util.Queue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

@ServerEndpoint("/hello")
public class HelloWorldResource {
	static Queue<Session> queue = new ConcurrentLinkedQueue<>();
	public static Logger logger = Logger.getLogger(HelloWorldResource.class);

	public static void send(String message) {
		try {
			for (Session session : queue) {
				session.getBasicRemote().sendText(message);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		queue.add(session);
		logger.info("Connection opend-----------------");
		HelloWorldResourceOne.sayHello();
	}

	@OnClose
	public void onClose(Session session) {
		queue.remove(session);
		logger.info("Connection closed-----------------");
	}

	@OnError
	public void onError(Session session, Throwable t) {
		queue.remove(session);
		logger.info("Connection closed there is some error -----------------");
	}

}