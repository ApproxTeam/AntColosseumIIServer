/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.gamelogic.interfaces;

import com.approxteam.antcolosseumserver.mailer.MailWrapper;
import javax.ejb.Remote;

/**
 *
 * @author adamr
 */
@Remote
public interface Mailer {
    public boolean send(MailWrapper wrapper);
}
