/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.consumers;

import com.approxteam.antcolosseumserver.PlayerHandler;
import com.approxteam.antcolosseumserver.context.ContextUtils;
import com.approxteam.antcolosseumserver.entities.Player;
import com.approxteam.antcolosseumserver.entities.PlayerActivation;
import com.approxteam.antcolosseumserver.gamelogic.Action;
import com.approxteam.antcolosseumserver.gamelogic.PlayerState;
import com.approxteam.antcolosseumserver.gamelogic.Response;
import com.approxteam.antcolosseumserver.gamelogic.ResponseType;
import com.approxteam.antcolosseumserver.gamelogic.SessionUtils;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.util.List;
import java.util.function.BiConsumer;

/**
 *
 * @author adamr
 */
public class LoginConsumer implements BiConsumer<PlayerHandler, Action> {
    @Override
    public void accept(PlayerHandler t, Action u) {
        RegisterBean bean = ContextUtils.getRegisterBean();
        Player player = bean.findPlayer(u);
        Response response = getProperlyResponse(player, u.getLoginDivision().getPassword());
        if(response.getType().equals(ResponseType.LOGINOK)) {
            t.switchState(PlayerState.CHOOSING);
        }
        SessionUtils.serializeAndSendAsynchronously(t, response);
    }
    
    private Response getProperlyResponse(Player player, String password) {
        if(player != null && player.getPassword().equals(password)) {
            if(!isActivated(player.getPlayerActivations())) {
                return Response.of(ResponseType.NOTACTIVATED);
            }
            return Response.of(ResponseType.LOGINOK);
        } else {
            return Response.of(ResponseType.LOGINERROR_BADLOGINORPASSWORD);
        }
    }
    
    private boolean isActivated(List<PlayerActivation> activations) {
        for(PlayerActivation activation: activations) {
            if(activation.isActivated()) {
                return true;
            }
        }
        return false;
    }
}
