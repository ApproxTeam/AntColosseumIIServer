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
public class Response implements Serializable {
    private ResponseType type;
    private String description;
    private String[] args = new String[0];

    public Response(ResponseType type) {
        this.type = type;
        this.description = type.getDescription();
    }
    
    public Response(ResponseType type, String ... args) {
        this.type = type;
        this.description = type.getDescription();
        this.args = args;
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
    
    
    
    
    
    
    public static Response of(ResponseType type) {
        return new Response(type);
    }
    
    public static Response of(ResponseType type, String ... args) {
        return new Response(type, args);
    }
}
