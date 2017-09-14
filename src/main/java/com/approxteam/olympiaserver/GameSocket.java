/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.olympiaserver;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author adamr
 */
@ApplicationScoped
@ServerEndpoint("/game")
public class GameSocket {

    @Inject
    private GameSessionHandler sessionHandler;
    
    @OnOpen
        public void open(Session session) {
            System.out.println(session.toString());
            sessionHandler.addSession(session);
            session.getAsyncRemote().sendText("TEST");
            
    }   
    @OnClose
        public void close(Session session) {
            sessionHandler.removeSession(session);
    }   

    @OnError
        public void onError(Throwable error) {
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
        System.out.println(message);
        try {
            session.getBasicRemote().sendText("TEST222");
        } catch (IOException ex) {
            session.getAsyncRemote().sendText("UNRECOGNIZED");
        }
    }
}
