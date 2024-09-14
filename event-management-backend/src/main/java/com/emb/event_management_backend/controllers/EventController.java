package com.emb.event_management_backend.controllers;

import com.emb.event_management_backend.Entity.Attendee;
import com.emb.event_management_backend.models.Event;
import com.emb.event_management_backend.services.AttendeeService;
import com.emb.event_management_backend.services.EmailService;
import com.emb.event_management_backend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000/")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AttendeeService attendeeService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/createevent")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PostMapping("/{id}/rsvp")
    public ResponseEntity<Attendee> rsvpToEvent(@PathVariable Long id, @RequestBody Attendee attendee) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            attendee.setEvent(event);
            Attendee savedAttendee = attendeeService.createAttendee(attendee);

            // Send email notification
            emailService.sendSimpleMessage(attendee.getEmail(), "RSVP Confirmation", "You have successfully RSVP'd to the event: " + event.getTitle());

            return ResponseEntity.ok(savedAttendee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
