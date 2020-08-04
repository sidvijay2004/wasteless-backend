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


import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
public class DonationController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private DonationRepository donationRepository;

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

        Page<Donation> page = donationRepository.findByDonorId(pageable, donorId);


        return page;
//        return donationRepository.findAll(pageable);
    }

    @GetMapping("/pickupList/{donorId}")
    public Page<Donation> pickupList(Pageable pageable, @PathVariable Long donorId) {

        System.out.println("Before pickup List");

        Optional<Participant> participant = participantRepository.findById(donorId);

        System.out.println("participant" + participant);


        Page<Donation> page = donationRepository.findByDonorIdNotAndStatusAndDonorCityAndDonorState(pageable, ""+ donorId, "Available", participant.get().getCity(), participant.get().getState());

        System.out.println("After pickuplist" + page);


        return page;
//        return donationRepository.findAll(pageable);
    }

    @GetMapping("/donation/{donationId}")
    public Optional<Donation> getDonation(@PathVariable Long donationId) {
        System.out.println("Inside get Donation Method: " + donationId);

//        Donation donation = donationRepository.getOne(donationId);
        Optional<Donation> donation = donationRepository.findById(donationId);

        System.out.println("Printing Donation " + donation);


        return donation;
//        return donationRepository.findAll(pageable);
    }


    @PostMapping("/donations")
    public Donation createDonation(@Valid @RequestBody Donation donation) {
        System.out.println("Inside Post Donation: " + new Date());
        System.out.println("Before Save = " + donation);
//        return donationRepository.save(donation);
        Donation saveDonation = donationRepository.save(donation);
        System.out.println("After Save = " + saveDonation);
        return saveDonation;
    }

    @PutMapping("/donations/{donationId}")
    public Donation updateDonation(@PathVariable Long donationId,
                                   @Valid @RequestBody Donation donationRequest) {
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
    public ResponseEntity<?> deleteDonation(@PathVariable Long donationId) {
        return donationRepository.findById(donationId)
                .map(donation -> {
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
