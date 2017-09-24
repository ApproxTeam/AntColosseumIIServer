/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author adamr
 */
@Stateless
public class WebSocketRegisterer implements RegisterBean{

    @PersistenceContext(unitName = "AntColosseumPU")
    private EntityManager entityManager;
    
    @Override
    public Response register(Action action) {
        String nickName = action.getRegisterDivisor().getLogin();
        String password = action.getRegisterDivisor().getPassword();
        String email = action.getRegisterDivisor().getEmail();
        Player player = new Player();
        player.setEmail(email);
        player.setPassword(password);
        player.setNickname(nickName);
        save(player);
        return null;
    }
    
    public void save(Object o) {
        entityManager.persist(o);
    }
    
}
