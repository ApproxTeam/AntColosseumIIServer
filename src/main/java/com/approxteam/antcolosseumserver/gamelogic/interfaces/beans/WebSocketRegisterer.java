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
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author adamr
 */
@Stateful
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
        try {
            save(player);
        } catch(HibernateException e) {
            return Response.of(ResponseType.REGISTERERROR_EMAILORLOGINEXIST);
        }
        return Response.of(ResponseType.REGISTEROK);
    }
    
    public void save(Object o) {
        entityManager.persist(o);
    }
    
}
