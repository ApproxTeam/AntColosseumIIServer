/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.olympiaserver;

import java.util.HashSet;
import java.util.Set;
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
}
