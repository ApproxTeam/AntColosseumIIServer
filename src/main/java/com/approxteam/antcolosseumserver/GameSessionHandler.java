/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author adamr
 */
@ApplicationScoped
public class GameSessionHandler {
    
    private static final Logger log = LogManager.getLogger(GameSessionHandler.class);
    
    @Inject
    private GameHandler gameHandler;
    
    private Map<Session, PlayerHandler> sessions = new HashMap<>();
    
    public void addSession(Session session) {
        PlayerHandler player = new PlayerHandler(session);
        sessions.put(session, player);
        gameHandler.addToPlayerList(player);
        log.info("NEW PLAYER: " + player);
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
        gameHandler.removeFromPlayerList(session);
        log.info("REMOVED PLAYER: " + session);
    }
    
    public void doActionOnAll(Consumer<Session> consumer) {
        for(Session session: sessions.keySet()) {
            consumer.accept(session);
        }
    }
    
    public void doActionOnWhen(Consumer<Session> consumer, Predicate<Session> ... predicates) {
        for(Session session: sessions.keySet()) {
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
    
    public PlayerHandler getPlayerBySession(Session session) {
        return this.sessions.get(session);
    }
}
