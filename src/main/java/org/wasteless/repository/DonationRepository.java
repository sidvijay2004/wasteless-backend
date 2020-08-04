
package org.wasteless.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.Donation;
import org.wasteless.model.Participant;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findByDonorId(Pageable pageable, String donorId);
    Page<Donation> findByDonorIdNotAndStatusAndDonorCityAndDonorState(Pageable pageable, String donorId, String status, String donorCity, String donorState);
    Page<Donation> findByDonorIdNot(Pageable pageable, String donorId);

}
