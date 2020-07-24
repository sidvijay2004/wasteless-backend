package org.wasteless.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
<<<<<<< HEAD
    private String description;
    @Column(name = "donorid")
    private String donorId;
    @Column(name = "donorname")
    private String donorName;
    @Column(name = "donationdt")
=======
    private String name;
    private String status;
    private Long donorId;
>>>>>>> 7c665e871289852e5ad2708ae764e6fc3c110108
    private Timestamp donationDt;

<<<<<<< HEAD
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    @Column(name = "volunteerid")
    private String volunteerId;
    @Column(name = "donorphone")
    private String donorPhone;
    @Column(name = "donoraddress1")
    private String donorAddress1;
    @Column(name = "donoraddress2")
    private String donorAddress2;
    @Column(name = "donorcity")
    private String donorCity;
    @Column(name = "donorstate")
    private String donorState;
    @Column(name = "donorcountry")
    private String donorCountry;

    public String getDonorZipcode() {
        return donorZipcode;
    }

    public void setDonorZipcode(String donorZipcode) {
        this.donorZipcode = donorZipcode;
    }

    @Column(name = "donorzipcode")
    private String donorZipcode;

    public Donation(Long id, String description, String donorId, String donorName, Timestamp donationDt, String status, String volunteerId, String donorPhone, String donorAddress1, String donorAddress2, String donorCity, String donorState, String donorCountry, String donorZipcode) {
        this.id = id;
        this.description = description;
=======
    public Donation(Long id, String name, String status,Long donorId, Timestamp donationDt, Long volunteerId) {
        this.id = id;
        this.name = name;
        this.status = status;
>>>>>>> 7c665e871289852e5ad2708ae764e6fc3c110108
        this.donorId = donorId;
        this.donorName = donorName;
        this.donationDt = donationDt;
        this.status = status;
        this.volunteerId = volunteerId;
        this.donorPhone = donorPhone;
        this.donorAddress1 = donorAddress1;
        this.donorAddress2 = donorAddress2;
        this.donorCity = donorCity;
        this.donorState = donorState;
        this.donorCountry = donorCountry;
        this.donorZipcode = donorZipcode;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", donorId='" + donorId + '\'' +
                ", donorName='" + donorName + '\'' +
                ", donationDt=" + donationDt +
                ", volunteerId='" + volunteerId + '\'' +
                ", donorPhone='" + donorPhone + '\'' +
                ", donorAddress1='" + donorAddress1 + '\'' +
                ", donorAddress2='" + donorAddress2 + '\'' +
                ", donorCity='" + donorCity + '\'' +
                ", donorState='" + donorState + '\'' +
                ", donorCountry='" + donorCountry + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

<<<<<<< HEAD
    public String getDonorId() {
=======
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDonorId() {
>>>>>>> 7c665e871289852e5ad2708ae764e6fc3c110108
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public Timestamp getDonationDt() {
        return donationDt;
    }

    public void setDonationDt(Timestamp donationDt) {
        this.donationDt = donationDt;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getDonorPhone() {
        return donorPhone;
    }

    public void setDonorPhone(String donorPhone) {
        this.donorPhone = donorPhone;
    }

    public String getDonorAddress1() {
        return donorAddress1;
    }

    public void setDonorAddress1(String donorAddress1) {
        this.donorAddress1 = donorAddress1;
    }

    public String getDonorAddress2() {
        return donorAddress2;
    }

    public void setDonorAddress2(String donorAddress2) {
        this.donorAddress2 = donorAddress2;
    }

    public String getDonorCity() {
        return donorCity;
    }

    public void setDonorCity(String donorCity) {
        this.donorCity = donorCity;
    }

    public String getDonorState() {
        return donorState;
    }

    public void setDonorState(String donorState) {
        this.donorState = donorState;
    }

    public String getDonorCountry() {
        return donorCountry;
    }

    public void setDonorCountry(String donorCountry) {
        this.donorCountry = donorCountry;
    }

    public Donation(){
        super();
    }

}