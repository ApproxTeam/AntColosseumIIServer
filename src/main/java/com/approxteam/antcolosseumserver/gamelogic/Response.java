/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic;

import com.approxteam.antcolosseumserver.gamelogic.notify.NotifyType;
import java.io.Serializable;

/**
 *
 * @author adamr
 */
public class Response implements Serializable {
    private String className = this.getClass().getSimpleName();
    private ResponseType type;
    private String description;
    private NotifyType notifyType;
    private String[] args;

    public Response(ResponseType type, String description, NotifyType notifyType, String ... args) {
        this.type = type;
        this.description = description;
        this.notifyType = notifyType;
        this.args = args;
    }
    
    public Response(ResponseType type) {
        this(type, type.getDescription(), type.getNotifyType(), new String[0]);
    }
    public Response(ResponseType type, NotifyType notifyType) {
        this(type, type.getDescription(), notifyType, new String[0]);
    }
    
    public Response(ResponseType type, String ... args) {
        this(type, type.getDescription(), type.getNotifyType(), args);
    }
    
    public Response(String description, ResponseType type, String ... args) {
        this(type, description, type.getNotifyType(), args);
    }
    
    public Response(ResponseType type, NotifyType notifyType, String ... args) {
        this(type, type.getDescription(), notifyType, args);
    }

    public ResponseType getType() {
        return type;
    }

    public String[] getArgs() {
        return args;
    }

    public String getDescription() {
        return description;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    } 

    public String getClassName() {
        return className;
    }
    
    
    
    public static Response of(ResponseType type) {
        return new Response(type);
    }
    
    public static Response of(ResponseType type, String ... args) {
        return new Response(type, args);
    }
    
    public static Response of(String description, ResponseType type, String ... args) {
        return new Response(description, type, args);
    }
    
    public static Response of(ResponseType type, NotifyType notifyType, String ... args) {
        return new Response(type, notifyType, args);
    }
    
    public static Response of(ResponseType type, String description, NotifyType notifyType, String ... args) {
        return new Response(type, description, notifyType, args);
    }
}
