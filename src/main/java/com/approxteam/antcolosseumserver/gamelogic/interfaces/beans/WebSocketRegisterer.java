/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Mailer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import com.approxteam.antcolosseumserver.mailer.MailWrapper;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.AroundInvoke;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author adamr
 */
@Stateful
public class WebSocketRegisterer implements RegisterBean{

    @PersistenceContext(unitName = "AntColosseumPU")
    private EntityManager entityManager;
    
    @EJB
    private Mailer mailer;
    
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(WebSocketRegisterer.class);
    
    @Override
    public boolean register(Action action){
        String nickName = action.getRegisterDivisor().getLogin();
        String password = action.getRegisterDivisor().getPassword();
        String email = action.getRegisterDivisor().getEmail();
        Player player = new Player();
        player.setEmail(email);
        player.setPassword(password);
        player.setNickname(nickName);
        mailer.send(constructActivationEmail(email));
        return save(player);
    }
    
    private MailWrapper constructActivationEmail(String to) {
        MailWrapper wrapper = new MailWrapper("antqueen@antcolosseum.pl", to, "REGISTER ACTIVATION", "TODO");
        return wrapper;
    }
   
    @Override
    public Player findPlayer(Action action) {
        String login = action.getLoginDivision().getLogin();
        return find(login);
        
    } 
    
    
    private Player find(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> player = cq.from(Player.class);
        ParameterExpression<String> name = cb.parameter(String.class);
        cq.select(player).where(cb.equal(player.get("nickname"), name));
        TypedQuery<Player> q = entityManager.createQuery(cq);
        q.setParameter(name, login);
        try {
            Player result = q.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
    
    
    
    private boolean save(Object o) {
        try {
            entityManager.persist(o);
            entityManager.flush();
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    
}
