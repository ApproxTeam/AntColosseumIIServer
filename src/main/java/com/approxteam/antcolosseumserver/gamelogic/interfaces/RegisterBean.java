/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces;

import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface RegisterBean {
    public boolean register(Action action);
    
    
    public void sendActivationLink(String email, String nickName, String token);
    
    public Player findPlayer(Action action);
}
