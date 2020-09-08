
package org.wasteless.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wasteless.model.Donation;
import org.wasteless.model.Participant;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findByDonorId(Pageable pageable, String donorId);
//    @Query(value = " SELECT d from donation d where CAST (d.donorid AS VARCHAR) = :donorid and d.status = :status and (d.donorCity = :donorCity or d.donorState = :donorState )",
//            countQuery = " SELECT count(*) from donation d where CAST (d.donorid AS VARCHAR) = :donorid and d.status = :status and (d.donorCity = :donorCity or d.donorState = :donorState )",
//            nativeQuery = true)
    Page<Donation> findByDonorIdNotAndStatusOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status );

    Page<Donation> findByDonorIdAndStatusNotOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status );

    Page<Donation> findByDonorIdNotAndStatusAndDonorCityAndDonorStateOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status, @Param("donorCity") Optional<String> donorCity, @Param("donorState") Optional<String> donorState);

    Page<Donation> findByDonorIdNotAndStatusAndDonorCityOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status, @Param("donorCity") Optional<String> donorCity);

    Page<Donation> findByDonorIdNotAndStatusAndDonorStateOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status, @Param("donorState") Optional<String> donorState);

    Page<Donation> findByDonorIdNotAndStatusAndDonorZipcodeOrderByDonationDtDesc(Pageable pageable, @Param("donorId") String donorId, @Param("status") String status, @Param("donorZipcode") Optional<String> donorZipcode);

    Page<Donation> findByVolunteerId(Pageable pageable, @Param("volunteerId") String volunteerId);

    Page<Donation> findByVolunteerIdAndStatusOrderByDonationDtDesc(Pageable pageable, @Param("volunteerId") String volunteerId, @Param("status") String status);


    Page<Donation> findByDonorIdNot(Pageable pageable, String donorId);

}
