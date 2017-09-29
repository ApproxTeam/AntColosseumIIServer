/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces;

import com.approxteam.antcolosseumserver.gamelogic.Action;
import javax.ejb.Remote;
import javax.persistence.PersistenceException;

/**
 *
 * @author adamr
 */
@Remote
public interface RegisterBean {
    public boolean register(Action action) throws Exception;
}
