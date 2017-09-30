/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.consumers;

import com.approxteam.antcolosseumserver.PlayerHandler;
import com.approxteam.antcolosseumserver.context.ContextUtils;
import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.PlayerState;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import com.approxteam.antcolosseumserver.gamelogic.ResponseType;
import com.approxteam.antcolosseumserver.gamelogic.SessionUtils;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.util.function.BiConsumer;

/**
 *
 * @author adamr
 */
public class LoginConsumer implements BiConsumer<PlayerHandler, Action> {
    @Override
    public void accept(PlayerHandler t, Action u) {
        RegisterBean bean = ContextUtils.getRegisterBean();
        Response response = Response.of(ResponseType.ERROR);
        Player player = bean.findPlayer(u);
        if(player != null && player.getPassword().equals(u.getLoginDivision().getPassword())) {
            response = Response.of(ResponseType.LOGINOK);
            t.switchState(PlayerState.CHOOSING);
        } else {
            response = Response.of(ResponseType.LOGINERROR_BADLOGINORPASSWORD);
        }
            
        SessionUtils.serializeAndSendAsynchronously(t, response);
        }
    
}
