/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import com.approxteam.antcolosseumserver.context.ContextUtils;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.persistence.PersistenceException;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public enum ActionConsumer implements Serializable {
    
    REGISTER(new BiConsumer<Session, Action>() { 
        
        @Override
        public void accept(Session t, Action u) {
            RegisterBean bean = ContextUtils.getRegisterBean();
            Response response = Response.of(ResponseType.ERROR);
            try {
                boolean registered = bean.register(u);
                if(registered) {
                    response = Response.of(ResponseType.REGISTEROK);
                }
            } catch(PersistenceException e) {
                response = Response.of(ResponseType.REGISTERERROR_EMAILORLOGINEXIST);
            }
            SessionUtils.serializeAndSendAsynchronously(t, response);
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
