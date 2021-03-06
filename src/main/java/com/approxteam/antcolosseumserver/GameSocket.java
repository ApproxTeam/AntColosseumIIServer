/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Mailer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Recognizer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.beans.WebSocketMailer;
import com.approxteam.antcolosseumserver.mailer.MailWrapper;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
    
    @EJB
    private Mailer mailer;
    
    private static final Logger log = LogManager.getLogger(GameSocket.class);
    
    @OnOpen
        public void open(Session session) {
            log.info("OPENED NEW WEBSOCKET SESSION: " + session.getId());
            sessionHandler.addSession(session);
            
    }   
    @OnClose
        public void close(Session session) {
            log.info("CLOSED WEBSOCKET SESSION: " + session.getId());
            sessionHandler.removeSession(session);
    }   

    @OnError
        public void onError(Throwable error) {
            
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        log.info("MESSAGE FROM " + session.getId() + ": " + message);
        Action action = recognizer.recognize(message);
        if(action != null) {
            if(action.getType().getConsumer() != null) {
                PlayerHandler playerHandler = sessionHandler.getPlayerBySession(session);
                action.getType().getConsumer().consume(playerHandler, action);
            }
        }
    }
}
