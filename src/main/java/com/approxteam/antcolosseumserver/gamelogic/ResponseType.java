/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import java.io.Serializable;

/**
 *
 * @author adamr
 */
public enum ResponseType implements Serializable {
    OK(),
    ERROR(),
    REGISTERERROR_EMAILORLOGINEXIST("E-mail or login exists"),
    REGISTEROK("Succesful registered");
    
    private String description;
    private String notifyType;

    private ResponseType(String description, String notifyType) {
        this.description = description;
        this.notifyType = notifyType;
    }
    
    private ResponseType(String description) {
        this(description, "TOAST");
    }
    
    private ResponseType() {
        this("");
    }

    public String getDescription() {
        return description;
    }
    
    
}
