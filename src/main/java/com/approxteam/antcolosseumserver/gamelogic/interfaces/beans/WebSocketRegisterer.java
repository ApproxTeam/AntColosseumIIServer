/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces.beans;

import com.approxteam.antcolosseumserver.configuration.PropertiesBuilder;
import com.approxteam.antcolosseumserver.configuration.PropertyComment;
import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.entities.PlayerActivation;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.Mailer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import com.approxteam.antcolosseumserver.mailer.MailWrapper;
import com.approxteam.antcolosseumserver.templates.ActivationMail;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@PropertyComment(desc = "Provide default link")
public class WebSocketRegisterer implements RegisterBean{

    private final Properties properties = PropertiesBuilder.getProperties(WebSocketRegisterer.class);
    
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
        
        boolean status = save(player);
        
        String token = getRandomToken();
        PlayerActivation playerActivation = new PlayerActivation();
        playerActivation.setPlayer(player);
        playerActivation.setActivated(false);
        playerActivation.setToken(token);
        
        boolean playerActivationStatus = save(playerActivation);
        if(playerActivationStatus) {
            sendActivationLink(email, nickName, token);
        }
        
        return status;
    }
    
    
    
    private MailWrapper constructActivationEmail(String to, String nickName, String token) {
        MailWrapper wrapper = new ActivationMail(to, constructActivationLink(token, nickName), nickName);
        return wrapper;
    }
    
    private String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    private String constructActivationLink(String token, String nickname) {
        return properties.getProperty("appLink") + "nickname=" + nickname + "&" + "token=" + token;
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

    @Override
    public void sendActivationLink(String email, String nickName, String token) {
        mailer.send(constructActivationEmail(email, nickName, token));
    }

    @Override
    public boolean activate(Action action) {
        String token = action.getActivateDivisor().getToken();
        PlayerActivation playerActivation = findActivation(token);
        if(playerActivation == null) {
            return false;
        }
        String actionNickName = action.getActivateDivisor().getNickname();
        if(!actionNickName.equals(playerActivation.getPlayer().getNickname())) {
            return false;
        }
        
        playerActivation.setActivated(true);
        playerActivation.setDateActivated(new Date());
        
        return save(playerActivation);
        
    }

    @Override
    public PlayerActivation findActivation(String token) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlayerActivation> cq = cb.createQuery(PlayerActivation.class);
        Root<PlayerActivation> playerActivation = cq.from(PlayerActivation.class);
        ParameterExpression<String> tokenParameter = cb.parameter(String.class);
        cq.select(playerActivation).where(cb.equal(playerActivation.get("token"), tokenParameter));
        TypedQuery<PlayerActivation> query = entityManager.createQuery(cq);
        query.setParameter(tokenParameter, token);
        try {
            PlayerActivation result = query.getSingleResult();
            return result;
        } catch(Exception e) {
            return null;
        }
    }
}
