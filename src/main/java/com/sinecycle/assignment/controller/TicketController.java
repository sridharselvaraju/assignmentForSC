package com.sinecycle.assignment.controller;

import com.sinecycle.assignment.entity.AgentInfo;
import com.sinecycle.assignment.entity.Ticket;
import com.sinecycle.assignment.repository.specification.SearchCriteria;
import com.sinecycle.assignment.service.TicketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

  @NonNull
  private final TicketService ticketService;

  @PostMapping("/create")
  public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
    try {
      ticketService.createTicket(ticket);
      return ResponseEntity.ok(ticket);
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/getTickets")
  public ResponseEntity<?> getAllTickets() {
    List<Ticket> tickets;
    try {
      tickets = ticketService.getAllTickets();
      return ResponseEntity.ok(tickets);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/getTicketsBySearchCriteria")
  public ResponseEntity<?> getAllTicketsBySearchCriteria(@RequestBody SearchCriteria searchCriteria) {
    List<Ticket> tickets;
    try {
      tickets = ticketService.getAllTickets(searchCriteria);
      return ResponseEntity.ok(tickets);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{ticketId}")
  public ResponseEntity<?> getTicketById(@PathVariable Long ticketId) {
    Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
    if (ticket.isPresent()) {
      return ResponseEntity.ok(ticket.get());
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/update/{ticketId}")
  public ResponseEntity<?> updateTicket(@RequestBody Ticket ticket, @PathVariable Long ticketId) {
    Optional<Ticket> updatedTicket = ticketService.updateTicket(ticket, ticketId);
    if (updatedTicket.isPresent()) {
      return ResponseEntity.ok("Ticket successfully updated");
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/updateStatus/{ticketId}/{status}")
  public ResponseEntity<?> updateTicketStatus(@PathVariable Long ticketId, @PathVariable String status) {
    Optional<Ticket> updatedTicket = ticketService.updateTicketStatus(ticketId, status);
    if (updatedTicket.isPresent()) {
      return ResponseEntity.ok("Ticket status successfully updated");
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/updateResponse/{ticketId}/{response}")
  public ResponseEntity<?> updateTicketResponse(@PathVariable Long ticketId, @PathVariable String response) {
    Optional<Ticket> updatedTicket = ticketService.updateTicketResponse(ticketId, response);
    if (updatedTicket.isPresent()) {
      return ResponseEntity.ok("Ticket Response updated");
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{ticketId}")
  public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
    boolean isTicketDeleted = ticketService.deleteTicket(ticketId);
    if (isTicketDeleted) {
      return ResponseEntity.ok("Ticket with id " + ticketId + "deleted successfully");
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }


  @PutMapping("/updateAssignee/{ticketId}")
  public ResponseEntity<?> updateAssignee(@RequestBody AgentInfo agentInfo, @PathVariable Long ticketId) {
    Optional<Ticket> updatedTicket = ticketService.updateAssignee(ticketId, agentInfo);
    if (updatedTicket.isPresent()) {
      return ResponseEntity.ok("Ticket id " + ticketId + "updated with agentInfo :" + agentInfo.getName());
    } else {
      return new ResponseEntity<>("Ticket id " + ticketId + "is not available", HttpStatus.NOT_FOUND);
    }
  }
}
