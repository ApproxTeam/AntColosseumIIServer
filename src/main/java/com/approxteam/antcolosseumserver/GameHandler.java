/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
@ApplicationScoped
public class GameHandler {
    
    private List<PlayerHandler> playerList = new ArrayList<>();
    
    public void addToPlayerList(PlayerHandler player) {
        this.playerList.add(player);
    }
    
    
    public void removeFromPlayerList(PlayerHandler player) {
        playerList.remove(player);
    }
    
    public void removeFromPlayerList(Session session) {
        Iterator<PlayerHandler> iterator = playerList.iterator();
        while(iterator.hasNext()) {
            PlayerHandler player = iterator.next();
            if(player.getSession().equals(session)) {
                iterator.remove();
                break;
            }
        }
    }
}
