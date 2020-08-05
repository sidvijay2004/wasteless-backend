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
    public Page<Donation> pickupList(Pageable pageable, @PathVariable String donorId, @RequestParam Optional<String>  city, @RequestParam Optional<String>  state, @RequestParam Optional<String>  zipcode) {

        System.out.println("Before pickup List");

//        Optional<Participant> participant = participantRepository.findById(donorId);

//        System.out.println("participant" + participantRequest);
        System.out.println("Before PickupList: city, state:  " + city + state);

        Page<Donation> page;
//        Page<Donation> page = donationRepository.findByDonorIdNotAndStatusAndDonorCityAndDonorState(pageable, ""+ donorId, "Available", city, state);
        if(zipcode.isPresent()){
            page = donationRepository.findByDonorIdNotAndStatusAndDonorZipcode(pageable, donorId, "Available", zipcode);

        }
        else if (city.isPresent() && state.isPresent()){
            page = donationRepository.findByDonorIdNotAndStatusAndDonorCityAndDonorState(pageable, donorId, "Available", city, state);

        }
        else if (city.isPresent()){
            page = donationRepository.findByDonorIdNotAndStatusAndDonorCity(pageable, donorId, "Available", city);

        }
        else{
            page = donationRepository.findByDonorIdNotAndStatusAndDonorState(pageable, donorId, "Available", state);

        }

//        Page<Donation> page = donationRepository.findByDonorIdNotAndStatusOrDonorCityOrDonorState(pageable, donorId, "Available", city, state);

        System.out.println("After PickupList: city, state:  " + city + state);


        return page;
//        return donationRepository.findAll(pageable);
    }


    @GetMapping("/myPickupList/{volunteerId}")
    public Page<Donation> myPickupList(Pageable pageable, @PathVariable String volunteerId) {

        System.out.println("Before mypickup List executes");


        Page<Donation> page = donationRepository.findByVolunteerId(pageable, ""+ volunteerId);

        System.out.println("page: " + page);

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

    @PutMapping("/updateTakenDonation/{donationId}")
    public Donation updateTakenDonation(@PathVariable Long donationId, @RequestParam String volunteerId) {

        return donationRepository.findById(donationId)
                .map(donation -> {
                    donation.setVolunteerId(volunteerId);
                    donation.setStatus("Taken");

                    return donationRepository.save(donation);
                }).orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + donationId));
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
