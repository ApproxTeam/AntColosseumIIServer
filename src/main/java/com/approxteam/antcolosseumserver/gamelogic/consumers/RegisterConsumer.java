/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.consumers;

import com.approxteam.antcolosseumserver.PlayerHandler;
import com.approxteam.antcolosseumserver.context.ContextUtils;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import com.approxteam.antcolosseumserver.gamelogic.ResponseType;
import com.approxteam.antcolosseumserver.gamelogic.SessionUtils;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.util.function.BiConsumer;

/**
 *
 * @author adamr
 */
public class RegisterConsumer implements BiConsumer<PlayerHandler, Action>  {

    @Override
    public void accept(PlayerHandler t, Action u) {
        RegisterBean bean = ContextUtils.getRegisterBean();
        Response response = Response.of(ResponseType.ERROR);
        boolean registered = bean.register(u);
        if(registered) {
            response = Response.of(ResponseType.REGISTEROK);
        } else {
            response = Response.of(ResponseType.REGISTERERROR_EMAILORLOGINEXIST);
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
    }
    
}
