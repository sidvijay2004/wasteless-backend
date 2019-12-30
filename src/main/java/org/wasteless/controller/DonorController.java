package org.wasteless.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wasteless.exception.ResourceNotFoundException;
import org.wasteless.model.Donor;
import org.wasteless.repository.DonorRepository;

import javax.validation.Valid;

@RestController
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @GetMapping("/donors")
    public Page<Donor> getDonors(Pageable pageable) {
        return donorRepository.findAll(pageable);
    }


    @PostMapping("/donors")
    public Donor createDonor(@Valid @RequestBody Donor donor) {
        return donorRepository.save(donor);
    }

    @PutMapping("/donors/{donorId}")
    public Donor updateDonor(@PathVariable Long donorId,
                                   @Valid @RequestBody Donor donorRequest) {
        return donorRepository.findById(donorId)
                .map(donor -> {
                    donor.setName(donorRequest.getName());
                    donor.setAddress1(donorRequest.getAddress1());
                    return donorRepository.save(donor);
                }).orElseThrow(() -> new ResourceNotFoundException("Donor not found with id " + donorId));
    }


    @DeleteMapping("/donors/{donorId}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long donorId) {
        return donorRepository.findById(donorId)
                .map(donor -> {
                    donorRepository.delete(donor);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Donor not found with id " + donorId));
    }
}
