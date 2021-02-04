/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */


package org.wasteless.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasteless.exception.ResourceNotFoundException;
import org.wasteless.model.Donation;
import org.wasteless.model.Participant;
import org.wasteless.repository.DonationRepository;
import org.wasteless.repository.ParticipantRepository;
import org.wasteless.util.EventService;



import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

import org.wasteless.util.Constants;
import org.wasteless.util.EmailService;

@RestController
public class DonationController {

    @Autowired
    private ParticipantRepository participantRepository;


    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/donations")
    public Page<Donation> getDonations(Pageable pageable) {
        System.out.println("Before find all Inside getDonations" + pageable);

        Page<Donation> page = donationRepository.findAll(pageable);

        System.out.println("After find all Inside getDonations" + page);

        return page;
//        return donationRepository.findAll(pageable);
    }

    @GetMapping("/mydonations/{donorId}")
    public Page<Donation> myDonations(Pageable pageable, @PathVariable String donorId) {
        eventService.createEvent(new Long (donorId),donorId, "myDonations");

        Page<Donation> page = donationRepository.findByDonorIdAndStatusNotOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_COMPLETED);


        return page;
//        return donationRepository.findAll(pageable);
    }

    @GetMapping("/pickupList/{donorId}")
    public Page<Donation> pickupList(Pageable pageable, @PathVariable String donorId, @RequestParam Optional<String>  city, @RequestParam Optional<String>  state, @RequestParam Optional<String>  zipcode) {

        eventService.createEvent(new Long (donorId),donorId, "pickupList");

        System.out.println("Before pickup List. donor id: " + donorId);

//        Optional<Participant> participant = participantRepository.findById(donorId);

//        System.out.println("participant" + participantRequest);
        System.out.println("Before PickupList: city:  " + city );
        System.out.println("Before PickupList:  state:  " + state );
        System.out.println("Before PickupList:  zipcode:  " + zipcode );

        Page<Donation> page;
        if(zipcode.isPresent()){
            System.out.println("Executing Zipcode logic");

            page = donationRepository.findByDonorIdNotAndStatusAndDonorZipcodeOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_AVALIABLE, zipcode);

        }
        else if (city.isPresent() && state.isPresent()){
            System.out.println("Executing City and State logic");

            page = donationRepository.findByDonorIdNotAndStatusAndDonorCityAndDonorStateOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_AVALIABLE, city, state);

        }
        else if (city.isPresent()){
            System.out.println("Executing City logic");

            page = donationRepository.findByDonorIdNotAndStatusAndDonorCityOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_AVALIABLE, city);

        }
        else if (state.isPresent()){
            System.out.println("Executing State logic");

            page = donationRepository.findByDonorIdNotAndStatusAndDonorStateOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_AVALIABLE, state);

        }    else{
            System.out.println("Executing just id and status logic");

            page = donationRepository.findByDonorIdNotAndStatusOrderByDonationDtDesc(pageable, donorId, Constants.DONATION_STATUS_AVALIABLE);

        }


        System.out.println("After PickupList: city, state:  " + city + state);


        return page;
    }


    @GetMapping("/myPickupList/{volunteerId}")
    public Page<Donation> myPickupList(Pageable pageable, @PathVariable String volunteerId) {

        eventService.createEvent(new Long (volunteerId), volunteerId, "myPickupList");


        System.out.println("MYpickupList Volunteer id: " + volunteerId);


        Page<Donation> page = donationRepository.findByVolunteerIdAndStatusOrderByDonationDtDesc(pageable, ""+ volunteerId, Constants.DONATION_STATUS_TAKEN);

        System.out.println("page: " + page);

        return page;
//        return donationRepository.findAll(pageable);
    }


    @GetMapping("/donation/{donationId}")
    public Optional<Donation> getDonation(@PathVariable Long donationId) {
        eventService.createEvent(donationId, donationId, "getDonation");

        System.out.println("Inside get Donation Method: " + donationId);

//        Donation donation = donationRepository.getOne(donationId);
        Optional<Donation> donation = donationRepository.findById(donationId);

        System.out.println("Printing Donation " + donation);


        return donation;
//        return donationRepository.findAll(pageable);
    }


    @PostMapping("/donations")
    public Donation createDonation(@Valid @RequestBody Donation donation) {
        eventService.createEvent(new Long (donation.getDonorId()), donation, "createDonation");

        System.out.println("Inside Post Donation: " + new Date());
        System.out.println("Before Save = " + donation);
//        return donationRepository.save(donation);
        Donation saveDonation = donationRepository.save(donation);
        System.out.println("After Save = " + saveDonation);
        return saveDonation;
    }

    @PutMapping("/updateTakenDonation/{donationId}")
    public Donation updateTakenDonation(@PathVariable Long donationId, @RequestParam String volunteerId) {

        eventService.createEvent(new Long (volunteerId), donationId, "updateTakenDonation");

        System.out.println(" updateTakenDonation");
        System.out.println(" donationId="+donationId);
        System.out.println(" volunteerId:"+volunteerId);

        return donationRepository.findById(donationId)
                .map(donation -> {
                    Optional<Participant> participant = participantRepository.findById(new Long(donation.getDonorId()));
                    Optional<Participant> volunteer = participantRepository.findById(new Long(volunteerId));
                    emailService.sendEmail(participant.get(), "Wasteless- Your Donation Status: " + donation.getDescription(), "Hello, \n\n There is a volunteer who has signed up to pick your donation! \n\n The volunteer's name is " +
                            volunteer.get().getName() + ". \n Their email is " + volunteer.get().getEmail() + ". \n Their phone number is " + volunteer.get().getPhone() + ". \n\n Please use this information to communicate with them about the donation." +
                            " \n\n\n Thanks, \n Wasteless Team");
                    emailService.sendEmail(volunteer.get(), "Wasteless- The Donation You Chose to Pickup: " + donation.getDescription(), "Hello, \n\n Thank you for signing up to pick up a donation from the Wasteless App! \n\n The donor's name is " +
                            participant.get().getName() + ". \n Their email is " + participant.get().getEmail() + ". \n Their phone number is " + participant.get().getPhone() + ". \n\n Please use this information to communicate with them about the donation." +
                            " \n\n\n Thanks, \n Wasteless Team");
                    donation.setVolunteerId(volunteerId);
                    donation.setStatus("Taken");
                    donation.setvolunteerName(volunteer.get().getName());

                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }

    @PutMapping("/cancelTakenDonation/{donationId}")
    public Donation cancelTakenDonation(@PathVariable Long donationId, @RequestParam String volunteerId) {
        eventService.createEvent(new Long (volunteerId), donationId, "cancelTakenDonation");

        System.out.println(" donationId="+donationId);
        System.out.println(" volunteerId:"+volunteerId);


        return donationRepository.findById(donationId)
                .map(donation -> {
                    String donorMsg = "";
                    String volunteerMsg = "";
                    if(volunteerId != null) {
                        if (donation.getDonorId().equals(volunteerId)) {
                            donorMsg = "you have";
                            volunteerMsg = "The donor has";
                        } else {
                            donorMsg = "the volunteer has";
                            volunteerMsg = "You have";
                        }
                        Optional<Participant> participant = participantRepository.findById(new Long(donation.getDonorId()));
                        Optional<Participant> volunteer = participantRepository.findById(new Long(donation.getVolunteerId()));
                        emailService.sendEmail(participant.get(), "Wasteless- Canceling Your Donation: " +
                                donation.getDescription(), "Hello, \n\n This is just a message to alert you that " + donorMsg + " cancelled this donation. This donation has now been put back in the " +
                                "pickup list for another volunteer to sign up for. If you wish to delete this donation from the pickup list, please login to the app and click on the delete icon on your donation. We apologize for any inconvenience this may have caused. " +
                                " \n\n\n Thanks, \n Wasteless Team");
                        emailService.sendEmail(volunteer.get(), "Wasteless- The Donation You Chose to Pickup Has Been Cancelled: " + donation.getDescription(), "Hello, \n\n " + volunteerMsg + " cancelled this item from being picked up. " +
                                " If you wish to pickup another donation, please login to the app and browse the \"Pickup\" page. We apologize for any inconvenience this may have caused." +
                                " \n\n\n Thanks, \n Wasteless Team");
                    }
                    donation.setVolunteerId(null);
                    donation.setStatus(Constants.DONATION_STATUS_AVALIABLE);

                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }

    @PutMapping("/completedDonation/{donationId}")
    public Donation completedDonation(@PathVariable Long donationId, @RequestParam String volunteerId) {
        eventService.createEvent(new Long (volunteerId), donationId, "completedDonation");

        System.out.println(" donationId="+donationId);

        return donationRepository.findById(donationId)
                .map(donation -> {

                    donation.setStatus(Constants.DONATION_STATUS_COMPLETED);

                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }

    @PutMapping("/donations/{donationId}")
    public Donation updateDonation(@PathVariable Long donationId,
                                   @Valid @RequestBody Donation donationRequest) {
        eventService.createEvent(new Long (donationRequest.getDonorId()), donationRequest, "updateDonation");

        System.out.println("Inside Update Donation");
        return donationRepository.findById(donationId)
                .map(donation -> {
                    donation.setDescription(donationRequest.getDescription());
                    donation.setDonorId(donationRequest.getDonorId());
                    donation.setDonationDt(donationRequest.getDonationDt());
                    donation.setVolunteerId(donationRequest.getVolunteerId());
                    donation.setStatus(donationRequest.getStatus());
                    donation.setDonorCity(donationRequest.getDonorCity());
                    donation.setDonorState(donationRequest.getDonorState());
                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }


    @DeleteMapping("/donations/{donationId}")

    public ResponseEntity<?> deleteDonation(@PathVariable Long donationId, @RequestParam String volunteerId) {

        return donationRepository.findById(donationId)
                .map(donation -> {
                    eventService.createEvent(new Long (donation.getDonorId()), donation, "deleteDonation");
                    donationRepository.delete(donation);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }

    @DeleteMapping("/donations/all")
    public void deleteAll() {
        System.out.println("Before Delete all");
        donationRepository.deleteAll();
        System.out.println(     "After Delete all");


    }
}
