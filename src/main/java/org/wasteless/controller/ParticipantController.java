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
import org.wasteless.util.SecurityService;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EventLogRepository eventLogRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SecurityService securityService;


    @GetMapping("/forgotPassword/{participantId}")
    public ResponseEntity<?> forgotPassword(@PathVariable Long participantId) {

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
        return participantRepository.save(participant);
    }

    @PutMapping("/donors/{donorId}")
    public Participant updateDonor(@PathVariable Long donorId,
                                   @Valid @RequestBody Participant participantRequest) {
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
                    return participantRepository.save(participant);
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + secToken));
    }


    @DeleteMapping("/donors/{donorId}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long donorId) {
        return participantRepository.findById(donorId)
                .map(participant -> {
                    participantRepository.delete(participant);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + donorId));
    }




    @PostMapping("/donors/login")
    public Participant loginDonor(@RequestBody Participant participant) {

        participant =  participantRepository.findByEmailAndPassword(participant.getEmail(), participant.getPassword());

        EventLog eventLog = new EventLog();
        eventLog.setEventName("login");


        if(participant == null) {
            eventLog.setParticipantId((long) 0);
            eventLog.setLogData("No Data");

        } else {
            eventLog.setParticipantId(participant.getId());
            eventLog.setLogData(participant.toString());
        }

        System.out.println("eventLog: " + eventLog);
        eventLogRepository.save(eventLog);

        return participant;
    }


}