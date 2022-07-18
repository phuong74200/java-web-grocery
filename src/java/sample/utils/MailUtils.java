/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.env.env;

/**
 *
 * @author phuon
 *
 * Common Email is sync so it blocking the app using thread to make it async
 */
public class MailUtils extends Thread {

    private static final Logger logger = LogManager.getLogger(MailUtils.class);

    private String html;
    private String target;

    public MailUtils(String html, String target) {
        this.html = html;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(env.GMAIL_ID, env.GMAIL_PASSWORD));
            email.setSSLOnConnect(true);
            email.setFrom(env.GMAIL_ID + "@gmail.com");
            email.setSubject("Grocery Order Mail");
            email.setHtmlMsg(this.html);
            email.addTo(this.target);
            email.send();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
