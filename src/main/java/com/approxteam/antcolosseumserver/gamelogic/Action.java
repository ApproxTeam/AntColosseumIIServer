/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import com.approxteam.antcolosseumserver.gamelogic.actionDivisors.RegisterDivision;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author adamr
 */
public class Action {
    @JsonView(Views.ActionView.class)
    private ActionType type;
    
    @JsonView(Views.RegisterActionView.class)
    private RegisterDivision registerDivisor;

    public ActionType getType() {
        return type;
    }

    public RegisterDivision getRegisterDivisor() {
        return registerDivisor;
    }

    @Override
    public String toString() {
        return "Action{" + "type=" + type + ", registerDivisor=" + registerDivisor + '}';
    }

    

    
    
    
    
}