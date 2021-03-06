/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.context;

import com.approxteam.antcolosseumserver.gamelogic.interfaces.Mailer;
import com.approxteam.antcolosseumserver.gamelogic.interfaces.RegisterBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author adamr
 */
public class ContextUtils {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ContextUtils.class);
    
    private static Context initialContext = null;
    
    private static Context getCtx() {
        if(initialContext == null) {
            try {
                initialContext = new InitialContext();
                return initialContext;
            } catch (NamingException ex) {
               log.error("FAILED TO GET INITIALCONTEXT - CONTEXTUTILS");
               return null;
            }
        } else {
            return initialContext;
        }
    }
    
    public static RegisterBean getRegisterBean() {
        RegisterBean registerer = null;
        try {
            Context context = getCtx();
            Object o = context.lookup("java:module/WebSocketRegisterer");
            registerer = (RegisterBean) PortableRemoteObject.narrow(o, RegisterBean.class);
        } catch (NamingException ex) {
            log.error("FAILED TO GET REGISTERBEAN - CONTEXTUTILS");
        }
        return registerer;
    }
    
    public static Mailer getMailer() {
        Mailer mailer = null;
        try {
            Context context = getCtx();
            Object o = context.lookup("java:module/WebSocketMailer");
            mailer = (Mailer) PortableRemoteObject.narrow(o, Mailer.class);
        } catch (NamingException ex) {
            log.error("FAILED TO GET MAILER - CONTEXTUTILS");
        }
        return mailer;
    }
}
