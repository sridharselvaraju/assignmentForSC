package com.sinecycle.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "DTA_TICKET")
@Data
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_ticket", sequenceName = "seq_ticket_id", schema = "sinecycle", allocationSize = 1)
public class Ticket extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ticket")
  private Long id;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "CUSTOMER", referencedColumnName = "CUSTOMER_NAME")
  private CustomerInfo Customer;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "AGENT_NAME")
  private AgentInfo assignedTo;

  @Column(name = "PRIORITY")
  private String priority;

  @Column(name = "STATUS")
  private String status;

  @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
  @JoinColumn(name = "TICKET_ID")
  @OrderBy("createDate DESC")
  @Fetch(value = FetchMode.SUBSELECT)
  @SortComparator(TicketResponse.CreatedDescComparator.class)
  @JsonIgnore
  private SortedSet<TicketResponse> response = new TreeSet<>();
}
