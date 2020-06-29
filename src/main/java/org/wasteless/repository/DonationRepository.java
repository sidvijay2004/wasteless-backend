
package org.wasteless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.Donation;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {


}