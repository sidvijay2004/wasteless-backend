package org.wasteless.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasteless.exception.ResourceNotFoundException;
import org.wasteless.model.Donation;
import org.wasteless.repository.DonationRepository;

import javax.validation.Valid;

@RestController
public class DonationController {

    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("/donations")
    public Page<Donation> getDonations(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }


    @PostMapping("/donations")
    public Donation createDonation(@Valid @RequestBody Donation donation) {
        return donationRepository.save(donation);
    }

    @PutMapping("/donations/{donationId}")
    public Donation updateDonation(@PathVariable Long donationId,
                                   @Valid @RequestBody Donation donationRequest) {
        return donationRepository.findById(donationId)
                .map(donation -> {
                    donation.setDescription(donationRequest.getDescription());
                    donation.setDonorId(donationRequest.getDonorId());
                    donation.setDonationDt(donationRequest.getDonationDt());
                    donation.setVolunteerId(donationRequest.getVolunteerId());
                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }


    @DeleteMapping("/donations/{donationId}")
    public ResponseEntity<?> deleteDonation(@PathVariable Long donationId) {
        return donationRepository.findById(donationId)
                .map(donation -> {
                    donationRepository.delete(donation);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
    }
}
