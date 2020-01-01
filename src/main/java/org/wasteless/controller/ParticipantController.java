package org.wasteless.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasteless.exception.ResourceNotFoundException;
import org.wasteless.model.Participant;
import org.wasteless.repository.ParticipantRepository;

import javax.validation.Valid;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

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


    @DeleteMapping("/donors/{donorId}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long donorId) {
        return participantRepository.findById(donorId)
                .map(participant -> {
                    participantRepository.delete(participant);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + donorId));
    }
}
