package org.wasteless.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String status;
    private Long donorId;
    private Timestamp donationDt;
    private Long volunteerId;

    public Donation(Long id, String name, String status,Long donorId, Timestamp donationDt, Long volunteerId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.donorId = donorId;
        this.donationDt = donationDt;
        this.volunteerId = volunteerId;
    }

    public Donation(){
        super();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public Timestamp getDonationDt() {
        return donationDt;
    }

    public void setDonationDt(Timestamp donationDt) {
        this.donationDt = donationDt;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", donorId=" + donorId +
                ", donationDt=" + donationDt +
                ", volunteerId=" + volunteerId +
                '}';
    }


}