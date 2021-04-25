package com.sinecycle.assignment.service;

import com.sinecycle.assignment.entity.Ticket;
import com.sinecycle.assignment.repository.TicketRepository;
import com.sinecycle.assignment.util.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleJobsService {

  @NonNull
  private final TicketRepository ticketRepository;

  @NonNull
  private final TicketService ticketService;

  @Scheduled(cron = "0 0 0 * * ?")
  public void updateResolvedTickets() {
    List<Ticket> resolvedTickets = ticketRepository.findByStatus(Constants.RESOLVED, LocalDate.now().minusDays(30).atTime(0, 0),
            LocalDate.now().minusDays(30).atTime(23, 59));
    for (Ticket resolvedTicket : resolvedTickets) {
      ticketService.updateTicketInfo(resolvedTicket, Constants.CLOSED);
    }
    ticketRepository.saveAll(resolvedTickets);
  }
}
