package com.sinecycle.assignment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Comparator;

@Data
@Entity
@Table(name = "DTA_TICKET_RESPONSE")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_ticket_respose", schema = "sinecycle", sequenceName = "seq_ticket_response_id", allocationSize = 1)
public class TicketResponse extends BaseEntity implements Comparable<TicketResponse> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ticket_respose")
  private Long id;

  @Column(name = "TICKET_RESPONSE")
  String ticketResponse;

  @Column(name = "TICKET_ID")
  Long ticketId;

  public TicketResponse(String ticketResponse, Long ticketId) {
    this.ticketResponse = ticketResponse;
    this.ticketId = ticketId;
  }

  public TicketResponse() {

  }

  public static class CreatedDescComparator implements Comparator<TicketResponse> {
    @Override
    public int compare(TicketResponse o1, TicketResponse o2) {
      int compare = o2.getCreateDate().compareTo(o1.getCreateDate());
      if (compare == 0) {
        compare = o2.getId().compareTo(o1.getId());
      }
      return compare;
    }
  }

  @Override
  public int compareTo(@NonNull TicketResponse o) {
    if (getCreateDate() == null) {
      return 1;
    }
    return o.getCreateDate().compareTo(getCreateDate());
  }
}
