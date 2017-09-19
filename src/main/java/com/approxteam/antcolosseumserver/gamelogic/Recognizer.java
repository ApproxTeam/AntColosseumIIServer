/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import javax.websocket.Session;

/**
 *
 * @author adamr
 */
public interface Recognizer {
    public void recognize(Session session, Action action);
}
