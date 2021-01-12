/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

package org.wasteless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

        List<Participant> findByName(String name);
        Participant findByEmailAndPassword(String email,String password);

        Participant findByEmail(String email);
}