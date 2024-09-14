package com.emb.event_management_backend.services;

import com.emb.event_management_backend.Entity.Attendee;
import com.emb.event_management_backend.repositories.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public Attendee createAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }
}