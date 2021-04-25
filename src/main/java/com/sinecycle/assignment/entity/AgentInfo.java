package com.sinecycle.assignment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "DTA_AGENT_INFO")
@SequenceGenerator(name = "seq_agent_info_id", schema = "sinecycle", sequenceName = "seq_agent_info_id",
        initialValue = 1, allocationSize = 1)
public class AgentInfo extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agent_info_id")
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(name = "AGENT_NAME", unique = true)
  String name;

  @Column(name = "TICKETS_COUNT")
  Long ticketsCount;

  @Column(name = "OPEN_COUNT")
  Long openCount;

  @Column(name = "CLOSED_COUNT")
  Long closedCount;

  @Column(name = "INPROGRESS_COUNT")
  Long inProgressCount;

  @Column(name = "RESOLVED_COUNT")
  Long resolvedCount;
}
