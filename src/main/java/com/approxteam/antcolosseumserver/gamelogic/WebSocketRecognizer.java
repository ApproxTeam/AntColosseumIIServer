/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import com.approxteam.antcolosseumserver.gamelogic.interfaces.Recognizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.websocket.Session;

/**
 *
 * @author adamr
 */
@Stateless
public class WebSocketRecognizer implements Recognizer{

    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public Action recognize(String data) {
        try {
            Action act = getAccordingBaseAction(data);
            act = act.getType().getActionFor(data);
            return act;
        } catch (IOException ex) {
            return null; 
        }
    }
    
    public Action getAccordingBaseAction(String data) throws IOException {
        return mapper.readerWithView(Views.ActionView.class).forType(Action.class).readValue(data);
    }
    
}
