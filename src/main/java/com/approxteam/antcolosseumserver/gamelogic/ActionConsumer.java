/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public enum ActionConsumer {
    
    REGISTER(new BiConsumer<Session, Action>() {

        @Override
        public void accept(Session t, Action u) {
            registerer.register(u);
        }
        
    });
    
    private BiConsumer<Session, Action> consumer;
    private Predicate<Session>[] predicates;
    
    @EJB
    private static WebSocketRegisterer registerer;    
    

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
