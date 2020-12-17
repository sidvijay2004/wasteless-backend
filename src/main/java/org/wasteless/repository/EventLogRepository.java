package org.wasteless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasteless.model.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

        }