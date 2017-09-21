/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces;

import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import javax.ejb.Local;

/**
 *
 * @author adamr
 */
@Local
public interface RegisterBean {
    public Response register(Action action);
}
