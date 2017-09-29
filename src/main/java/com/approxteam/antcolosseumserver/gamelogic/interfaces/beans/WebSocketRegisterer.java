/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.interceptor.AroundInvoke;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author adamr
 */
@Stateful
public class WebSocketRegisterer implements RegisterBean{

    @PersistenceContext(unitName = "AntColosseumPU")
    private EntityManager entityManager;
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketRegisterer.class);
    
    @Override
    public boolean register(Action action) throws Exception{
        String nickName = action.getRegisterDivisor().getLogin();
        String password = action.getRegisterDivisor().getPassword();
        String email = action.getRegisterDivisor().getEmail();
        Player player = new Player();
        player.setEmail(email);
        player.setPassword(password);
        player.setNickname(nickName);
        try {
            save(player);
        } catch (Exception ex) {
            log.info(ex);
            throw ex;
        }
        return true;
    }
    
    public void save(Object o) throws Exception {
        entityManager.persist(o);
        entityManager.flush();
    }
    
}
