package com.emb.event_management_backend.repositories;

import com.emb.event_management_backend.Entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
