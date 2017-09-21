/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.WebSocketRecognizer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Recognizer;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@Singleton
@ServerEndpoint("/game")
public class GameSocket {

    @Inject
    private GameSessionHandler sessionHandler;
    
    @EJB
    private Recognizer recognizer;
    
    private static final Logger log = LogManager.getLogger(GameSocket.class);
    
    @OnOpen
        public void open(Session session) {
            log.info("CONNECTED: " + session.getRequestURI());
            sessionHandler.addSession(session);
            
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
        log.info("MESSAGE: " + message);
        Action action = recognizer.recognize(message);
        log.info("ACTION: " + action);
        if(action != null) {
            if(action.getType().getConsumer() != null) {
                action.getType().getConsumer().consume(session, action);
            }
        }
    }
}
