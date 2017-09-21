/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public enum ActionConsumer {
    
    REGISTER(new BiConsumer<Session, Action>() { 
        
        @EJB(lookup = "java:module/WebSocketRegisterer")
        private RegisterBean registerer;       
        
        @Override
        public void accept(Session t, Action u) {
            registerer.register(u);
        }
        
    });
    
    private BiConsumer<Session, Action> consumer;
    private Predicate<Session>[] predicates;
    
    

    private ActionConsumer(BiConsumer<Session, Action> consumer, Predicate<Session>[] predicates) {
        this.consumer = consumer;
        this.predicates = predicates;
    }

    private ActionConsumer(BiConsumer<Session, Action> consumer) {
        this.consumer = consumer;
    }
    
    public void consume(Session session, Action action) {
        this.consumer.accept(session, action);
    }
    
    
    
    
    
    
}
