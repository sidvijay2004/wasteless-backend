package org.wasteless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.Participant;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

        List<Participant> findByName(String name);
        Participant findByEmailAndPassword(String email,String password);
        }