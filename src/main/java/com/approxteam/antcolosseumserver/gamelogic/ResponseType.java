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
    OK("Success"),
    ERROR("Undefined Error", NotifyType.TOASTERROR),
    REGISTERERROR_EMAILORLOGINEXIST("E-mail or login exists", NotifyType.TOASTERROR),
    REGISTEROK("Succesful registered");
    
    private String description;
    private NotifyType notifyType;

    private ResponseType(String description, NotifyType notifyType) {
        this.description = description;
        this.notifyType = notifyType;
    }
    
    private ResponseType(String description) {
        this(description, NotifyType.TOASTOK);
    }
    
    private ResponseType() {
        this("", NotifyType.TOASTOK);
    }
    
    private ResponseType(NotifyType notifyType) {
        this("", notifyType);
    }

    public String getDescription() {
        return description;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }
    
    
    
    
}
