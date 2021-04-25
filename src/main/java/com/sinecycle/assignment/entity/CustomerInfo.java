package com.sinecycle.assignment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "DTA_CUSTOMER_INFO")
@SequenceGenerator(name = "seq_customer_info", sequenceName = "seq_customer_info_id", allocationSize = 1, schema = "sinecycle")
public class CustomerInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer_info")
  private Long id;

  @Column(name = "CUSTOMER_NAME")
  String customerName;

  @Column(name = "CUSTOMER_MAIL_ID")
  String customerMailId;

  @Column(name = "CUSTOMER_ROLE")
  String role;
}
