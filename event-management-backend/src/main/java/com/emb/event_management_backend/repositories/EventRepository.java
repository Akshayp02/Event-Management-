package com.emb.event_management_backend.repositories;

import com.emb.event_management_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
