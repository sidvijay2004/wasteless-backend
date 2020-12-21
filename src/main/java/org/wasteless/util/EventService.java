package org.wasteless.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wasteless.model.EventLog;
import org.wasteless.repository.EventLogRepository;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
//
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
////import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import freemarker.template.Template;
//import freemarker.template.TemplateException;


@Service("EventService")
public class EventService {
    @Autowired
    private EventLogRepository eventLogRepository;



    public void createEvent(Long participantId, Object object, String eventName) {
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