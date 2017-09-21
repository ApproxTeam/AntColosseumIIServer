/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public enum ActionConsumer {
    REGISTER(new Consumer<Session>() {
        @Override
        public void accept(Session t) {
            
        }
        
    });
    
    private Consumer<Session> consumer;
    private Predicate<Session>[] predicates;
    

    private ActionConsumer(Consumer<Session> consumer, Predicate<Session>[] predicates) {
        this.consumer = consumer;
        this.predicates = predicates;
    }

    private ActionConsumer(Consumer<Session> consumer) {
        this.consumer = consumer;
    }
    
    
    
    
    
    
}
