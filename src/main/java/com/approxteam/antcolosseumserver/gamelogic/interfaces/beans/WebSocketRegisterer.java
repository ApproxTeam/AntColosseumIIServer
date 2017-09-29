/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import com.approxteam.antcolosseumserver.gamelogic.ResponseType;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.sql.SQLException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author adamr
 */
@Stateful
public class WebSocketRegisterer implements RegisterBean{

    @PersistenceContext(unitName = "AntColosseumPU")
    private EntityManager entityManager;
    
    @Override
    public boolean register(Action action) throws PersistenceException{
        String nickName = action.getRegisterDivisor().getLogin();
        String password = action.getRegisterDivisor().getPassword();
        String email = action.getRegisterDivisor().getEmail();
        Player player = new Player();
        player.setEmail(email);
        player.setPassword(password);
        player.setNickname(nickName);
        try {
            save(player);
        } catch(PersistenceException e) {
            throw e;
        }
        return true;
    }
    
    public void save(Object o) {
        entityManager.persist(o);
    }
    
}
