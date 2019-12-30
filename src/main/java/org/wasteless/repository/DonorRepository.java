package org.wasteless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.Donor;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByName(String name);

}