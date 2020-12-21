package org.wasteless.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.wasteless.model.EventLog;
import org.wasteless.model.Participant;
import org.wasteless.repository.EventLogRepository;
//
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
////import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import freemarker.template.Template;
//import freemarker.template.TemplateException;


@Service("SecurityService")
public class SecurityService {
    String key = "Bar12345Bar12345  ";
    Cipher cipher;
    Key aesKey;

    @Autowired
    private EventLogRepository eventLogRepository;

    public String encrypt(String value)
    {
        try
        {
            System.out.println("value: " + value);
            // Create key and cipher
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
            // encrypt the text
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(value.getBytes());


            return new String(encrypted);
            // decrypt the text
//            cipher.init(Cipher.DECRYPT_MODE, aesKey);
//            String decrypted = new String(cipher.doFinal(encrypted));
//            System.err.println(decrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String value)
    {
        try
        {
            System.out.println("value: " + value);
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(value.getBytes()));
            return decrypted;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void createEvent(Long participantId, Object object, String eventName) {
        EventLog eventLog = new EventLog();
        eventLog.setEventName(eventName);

        if(participantId == null) {
            eventLog.setParticipantId((long) 0);
        } else {
            eventLog.setParticipantId(participantId);
        }


        if(object == null) {
            eventLog.setLogData("No Data");

        } else {
            eventLog.setLogData(object.toString());
        }

        System.out.println("eventLog: " + eventLog);
        eventLogRepository.save(eventLog);
    }






}