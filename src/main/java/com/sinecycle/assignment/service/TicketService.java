package com.sinecycle.assignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinecycle.assignment.entity.AgentInfo;
import com.sinecycle.assignment.entity.Ticket;
import com.sinecycle.assignment.entity.TicketResponse;
import com.sinecycle.assignment.repository.TicketRepository;
import com.sinecycle.assignment.repository.TicketResponseRepository;
import com.sinecycle.assignment.repository.specification.SearchCriteria;
import com.sinecycle.assignment.repository.specification.TicketSpecification;
import com.sinecycle.assignment.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.sinecycle.assignment.util.Constants.FROM_ADDRESS;

@Component
@Transactional
@RequiredArgsConstructor
public class TicketService {

  @NonNull
  private final TicketRepository ticketRepository;
  @NonNull
  private final ObjectMapper objectMapper;
  @NonNull
  private final AgentService assigneeService;
  @NonNull
  private final SendGridEmailService sendGridEmailService;
  @NonNull
  private final TicketResponseRepository ticketResponseRepository;

  public Ticket createTicket(Ticket ticket) {
    if (null == ticket.getStatus() || ticket.getStatus().isEmpty()) {
      ticket.setStatus(Constants.NEW);
    }
    AgentInfo agentInfo = null;
    if (null == ticket.getAssignedTo()) {
      agentInfo = assigneeService.getAgentsByTicketCount().isEmpty() ? null : assigneeService.getAgentsByTicketCount().get(0);
    }
    ticket.setAssignedTo(agentInfo);
    agentInfo = assigneeService.updateAssigneeInfo(ticket.getStatus(), ticket.getAssignedTo(), true, false);
    return ticketRepository.save(ticket);
  }

  public List<Ticket> getAllTickets() {
    return (List<Ticket>) ticketRepository.findAll();
  }

  public List<Ticket> getAllTickets(SearchCriteria searchCriteria) {
    return (List<Ticket>) ticketRepository.findAll(TicketSpecification.withSearchCriteria(searchCriteria));
  }

  public Optional<Ticket> getTicketById(Long ticketId) {
    return ticketRepository.findById(ticketId);
  }

  public Optional<Ticket> updateTicket(Ticket ticket, Long ticketId) {
    if (getTicketById(ticketId).isPresent()) {
      return Optional.ofNullable(ticketRepository.save(ticket));
    }
    return Optional.empty();
  }

  public Optional<Ticket> updateTicketStatus(Long ticketId, String status) {
    Optional<Ticket> ticket = getTicketById(ticketId);
    if (ticket.isPresent()) {
      Ticket updatedTicket = updateTicketInfo(ticket.get(), status);
      return Optional.ofNullable(ticketRepository.save(updatedTicket));
    }
    return Optional.empty();
  }

  public Ticket updateTicketInfo(Ticket ticket, String status) {
    AgentInfo agentInfo = assigneeService.updateAssigneeInfo(ticket.getStatus(), ticket.getAssignedTo(), false, false);
    ticket.setStatus(status);
    agentInfo = assigneeService.updateAssigneeInfo(status, ticket.getAssignedTo(), true, false);
    ticket.setAssignedTo(agentInfo);
    return ticket;
  }

  public Optional<Ticket> updateTicketResponse(Long ticketId, String response) {
    Optional<Ticket> ticket = getTicketById(ticketId);
    if (ticket.isPresent()) {
      TicketResponse ticketResponse = new TicketResponse(response, ticket.get().getId());
      ticket.get().getResponse().add(ticketResponse);
      sendGridEmailService.sendHtml(FROM_ADDRESS, ticket.get().getCustomer().getCustomerMailId(), ticket.get().getTitle(),
              ticket.get().getResponse().first().getTicketResponse());
      return Optional.ofNullable(ticketRepository.save(ticket.get()));
    }
    return Optional.empty();
  }

  public Optional<Ticket> updateAssignee(Long ticketId, AgentInfo agentInfo) {
    Optional<Ticket> ticket = getTicketById(ticketId);
    if (ticket.isPresent()) {
      assigneeService.updateAssigneeInfo(ticket.get().getStatus(), ticket.get().getAssignedTo(), false, true);
      agentInfo = assigneeService.updateAssigneeInfo(ticket.get().getStatus(), agentInfo, true, false);
      ticket.get().setAssignedTo(agentInfo);
      return Optional.ofNullable(ticketRepository.save(ticket.get()));
    }
    return Optional.empty();
  }

  public boolean deleteTicket(Long ticketId) {
    Optional<Ticket> ticket = getTicketById(ticketId);
    if (ticket.isPresent()) {
      ticketRepository.delete(ticket.get());
      return true;
    }
    return false;
  }
}
