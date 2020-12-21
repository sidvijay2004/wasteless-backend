package org.wasteless.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasteless.exception.ResourceNotFoundException;
import org.wasteless.model.EventLog;
import org.wasteless.model.Participant;
import org.wasteless.repository.EventLogRepository;
import org.wasteless.repository.ParticipantRepository;
import org.wasteless.util.EmailService;
import org.wasteless.util.EventService;
import org.wasteless.util.SecurityService;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private EventService eventService;


    @GetMapping("/forgotPassword/{participantId}")
    public ResponseEntity<?> forgotPassword(@PathVariable Long participantId) {
        eventService.createEvent(participantId, participantId, "forgotPassword");

        return participantRepository.findById(participantId)
                .map(participant -> {
                    System.out.println("participant: " + participant);
                    emailService.sendEmail(participant);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + participantId));
    }

//
//    @GetMapping("/forgotPassword/{participantId}")
//    public ResponseEntity<?> forgotPassword(@PathVariable Long participantId) {
////        participantRepository.findById(participantId)
////                .map(participant -> {
////                    emailService.sendEmail(participant.getEmail());
////                    return ResponseEntity.ok().build();
////                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + participantId));
//
//         participantRepository.findById(participantId)
//                .map(participant -> {
//                    participantRepository.delete(participant);
//                    return ResponseEntity.ok().build();
//                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + participantId));
//
//
//    }

    @GetMapping("/donors")
    public Page<Participant> getDonors(Pageable pageable) {
        return participantRepository.findAll(pageable);
    }

    @PostMapping("/donors")
    public Participant createDonor(@Valid @RequestBody Participant participant) {
        eventService.createEvent(participant.getId(), participant, "createDonor");
        return participantRepository.save(participant);
    }

    @PutMapping("/donors/{donorId}")
    public Participant updateDonor(@PathVariable Long donorId,
                                   @Valid @RequestBody Participant participantRequest) {
        eventService.createEvent(participantRequest.getId(), participantRequest, "updateDonor");

        return participantRepository.findById(donorId)
                .map(participant -> {
                    participant.setName(participantRequest.getName());
                    participant.setEmail(participantRequest.getEmail());
                    participant.setPassword(participantRequest.getPassword());
                    participant.setPhone(participantRequest.getPhone());
                    participant.setAddress1(participantRequest.getAddress1());
                    participant.setAddress2(participantRequest.getAddress2());
                    participant.setCity(participantRequest.getCity());
                    participant.setState(participantRequest.getState());
                    participant.setZipcode(participantRequest.getZipcode());
                    participant.setCountry(participantRequest.getCountry());

                    return participantRepository.save(participant);
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + donorId));
    }

    @PutMapping("/changePassword/{secToken}")
    public Participant changePassword(@PathVariable String secToken, @RequestBody String newPassword) {
        System.out.println("Security Token = " + secToken + " newPassword: " + newPassword);
        String decryptMess = securityService.decrypt(secToken);
        System.out.println("Security decrypt: " + decryptMess);
        Long donorId = new Long(decryptMess);
        System.out.println("Security Token (Doner ID) = " + donorId);

        return participantRepository.findById(donorId)
                .map(participant -> {
                    participant.setPassword(newPassword);
                    eventService.createEvent(participant.getId(), participant, "changePassword");
                    return participantRepository.save(participant);
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + secToken));
    }


    @DeleteMapping("/donors/{donorId}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long donorId) {
        eventService.createEvent(donorId, donorId, "deleteDonor");

        return participantRepository.findById(donorId)
                .map(participant -> {
                    participantRepository.delete(participant);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + donorId));
    }




    @PostMapping("/donors/login")
    public Participant loginDonor(@RequestBody Participant participant) {

        participant =  participantRepository.findByEmailAndPassword(participant.getEmail(), participant.getPassword());

        Long participantId = null;

        if(participant != null){
            participantId = participant.getId();
        }

        eventService.createEvent(participantId,participant, "Login");

        return participant;
    }


}