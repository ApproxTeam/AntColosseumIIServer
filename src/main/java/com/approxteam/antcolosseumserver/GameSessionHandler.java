/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
@ApplicationScoped
public class GameSessionHandler {
    private final Set<Session> sessions = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void doActionOnAll(Consumer<Session> consumer) {
        for(Session session: sessions) {
            consumer.accept(session);
        }
    }
    
    public void doActionOnWhen(Consumer<Session> consumer, Predicate<Session> ... predicates) {
        for(Session session: sessions) {
            for(Predicate<Session> predicate: predicates) {
                if(!predicate.test(session)) {
                    return;
                }
            }
            consumer.accept(session);
        }
    }
    
    public void doActionOn(Session session, Consumer<Session> consumer) {
        consumer.accept(session);
    }
}
