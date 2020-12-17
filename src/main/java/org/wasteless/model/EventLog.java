package org.wasteless.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "participantid")
    private Long participantId;

    @Column(name = "eventname")
    private String eventName;

    @Column(name = "logdata")
    private String logData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLog eventLog = (EventLog) o;
        return Objects.equals(id, eventLog.id) &&
                Objects.equals(participantId, eventLog.participantId) &&
                Objects.equals(eventName, eventLog.eventName) &&
                Objects.equals(logData, eventLog.logData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, participantId, eventName, logData);
    }

    @Override
    public String toString() {
        return "EventLog{" +
                "id=" + id +
                ", participantId=" + participantId +
                ", eventName='" + eventName + '\'' +
                ", logData='" + logData + '\'' +
                '}';
    }
}